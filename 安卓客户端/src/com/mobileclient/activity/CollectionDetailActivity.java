package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Collection;
import com.mobileclient.service.CollectionService;
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
public class CollectionDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明收藏id控件
	private TextView TV_collectId;
	// 声明被收藏小说控件
	private TextView TV_novelObj;
	// 声明收藏用户控件
	private TextView TV_userObj;
	// 声明收藏时间控件
	private TextView TV_collectTime;
	/* 要保存的收藏信息 */
	Collection collection = new Collection(); 
	/* 收藏管理业务逻辑层 */
	private CollectionService collectionService = new CollectionService();
	private NovelService novelService = new NovelService();
	private UserInfoService userInfoService = new UserInfoService();
	private int collectId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.collection_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看收藏详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_collectId = (TextView) findViewById(R.id.TV_collectId);
		TV_novelObj = (TextView) findViewById(R.id.TV_novelObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_collectTime = (TextView) findViewById(R.id.TV_collectTime);
		Bundle extras = this.getIntent().getExtras();
		collectId = extras.getInt("collectId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CollectionDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    collection = collectionService.GetCollection(collectId); 
		this.TV_collectId.setText(collection.getCollectId() + "");
		Novel novelObj = novelService.GetNovel(collection.getNovelObj());
		this.TV_novelObj.setText(novelObj.getNovelName());
		UserInfo userObj = userInfoService.GetUserInfo(collection.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_collectTime.setText(collection.getCollectTime());
	} 
}
