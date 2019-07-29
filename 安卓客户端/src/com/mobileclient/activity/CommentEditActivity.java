package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Comment;
import com.mobileclient.service.CommentService;
import com.mobileclient.domain.Novel;
import com.mobileclient.service.NovelService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class CommentEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明评论idTextView
	private TextView TV_commentId;
	// 声明被评小说下拉框
	private Spinner spinner_novelObj;
	private ArrayAdapter<String> novelObj_adapter;
	private static  String[] novelObj_ShowText  = null;
	private List<Novel> novelList = null;
	/*被评小说管理业务逻辑层*/
	private NovelService novelService = new NovelService();
	// 声明评论内容输入框
	private EditText ET_content;
	// 声明评论人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*评论人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明评论时间输入框
	private EditText ET_commentTime;
	protected String carmera_path;
	/*要保存的评论信息*/
	Comment comment = new Comment();
	/*评论管理业务逻辑层*/
	private CommentService commentService = new CommentService();

	private int commentId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.comment_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑评论信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_commentId = (TextView) findViewById(R.id.TV_commentId);
		spinner_novelObj = (Spinner) findViewById(R.id.Spinner_novelObj);
		// 获取所有的被评小说
		try {
			novelList = novelService.QueryNovel(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int novelCount = novelList.size();
		novelObj_ShowText = new String[novelCount];
		for(int i=0;i<novelCount;i++) { 
			novelObj_ShowText[i] = novelList.get(i).getNovelName();
		}
		// 将可选内容与ArrayAdapter连接起来
		novelObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, novelObj_ShowText);
		// 设置图书类别下拉列表的风格
		novelObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_novelObj.setAdapter(novelObj_adapter);
		// 添加事件Spinner事件监听
		spinner_novelObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				comment.setNovelObj(novelList.get(arg2).getNovelId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_novelObj.setVisibility(View.VISIBLE);
		ET_content = (EditText) findViewById(R.id.ET_content);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的评论人
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				comment.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_commentTime = (EditText) findViewById(R.id.ET_commentTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		commentId = extras.getInt("commentId");
		/*单击修改评论按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取评论内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(CommentEditActivity.this, "评论内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					comment.setContent(ET_content.getText().toString());
					/*验证获取评论时间*/ 
					if(ET_commentTime.getText().toString().equals("")) {
						Toast.makeText(CommentEditActivity.this, "评论时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_commentTime.setFocusable(true);
						ET_commentTime.requestFocus();
						return;	
					}
					comment.setCommentTime(ET_commentTime.getText().toString());
					/*调用业务逻辑层上传评论信息*/
					CommentEditActivity.this.setTitle("正在更新评论信息，稍等...");
					String result = commentService.UpdateComment(comment);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    comment = commentService.GetComment(commentId);
		this.TV_commentId.setText(commentId+"");
		for (int i = 0; i < novelList.size(); i++) {
			if (comment.getNovelObj() == novelList.get(i).getNovelId()) {
				this.spinner_novelObj.setSelection(i);
				break;
			}
		}
		this.ET_content.setText(comment.getContent());
		for (int i = 0; i < userInfoList.size(); i++) {
			if (comment.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_commentTime.setText(comment.getCommentTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
