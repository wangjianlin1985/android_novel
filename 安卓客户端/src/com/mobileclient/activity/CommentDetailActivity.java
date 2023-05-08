package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Comment;
import com.mobileclient.service.CommentService;
import com.mobileclient.domain.Novel;
import com.mobileclient.service.NovelService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class CommentDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明评论id控件
	private TextView TV_commentId;
	// 声明被评小说控件
	private TextView TV_novelObj;
	// 声明评论内容控件
	private TextView TV_content;
	// 声明评论人控件
	private TextView TV_userObj;
	// 声明评论时间控件
	private TextView TV_commentTime;
	/* 要保存的评论信息 */
	Comment comment = new Comment(); 
	/* 评论管理业务逻辑层 */
	private CommentService commentService = new CommentService();
	private NovelService novelService = new NovelService();
	private UserInfoService userInfoService = new UserInfoService();
	private int commentId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.comment_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看评论详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_commentId = (TextView) findViewById(R.id.TV_commentId);
		TV_novelObj = (TextView) findViewById(R.id.TV_novelObj);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_commentTime = (TextView) findViewById(R.id.TV_commentTime);
		Bundle extras = this.getIntent().getExtras();
		commentId = extras.getInt("commentId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CommentDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    comment = commentService.GetComment(commentId); 
		this.TV_commentId.setText(comment.getCommentId() + "");
		Novel novelObj = novelService.GetNovel(comment.getNovelObj());
		this.TV_novelObj.setText(novelObj.getNovelName());
		this.TV_content.setText(comment.getContent());
		UserInfo userObj = userInfoService.GetUserInfo(comment.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_commentTime.setText(comment.getCommentTime());
	} 
}
