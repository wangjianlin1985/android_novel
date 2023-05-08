package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Novel;
import com.mobileclient.service.NovelService;
import com.mobileclient.domain.NovelClass;
import com.mobileclient.service.NovelClassService;
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
public class NovelDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明小说id控件
	private TextView TV_novelId;
	// 声明小说类别控件
	private TextView TV_novelClassObj;
	// 声明小说名称控件
	private TextView TV_novelName;
	// 声明小说图片图片框
	private ImageView iv_novelPhoto;
	// 声明作者控件
	private TextView TV_author;
	// 声明出版社控件
	private TextView TV_publish;
	// 声明出版日期控件
	private TextView TV_publishDate;
	// 声明页数控件
	private TextView TV_novelPageNum;
	// 声明字数控件
	private TextView TV_wordsNum;
	// 声明小说文件图片框
	private ImageView iv_novelFile;
	// 声明是否推荐控件
	private TextView TV_tjFlag;
	// 声明阅读量控件
	private TextView TV_readCount;
	// 声明小说简介控件
	private TextView TV_novelDesc;
	/* 要保存的小说信息 */
	Novel novel = new Novel(); 
	/* 小说管理业务逻辑层 */
	private NovelService novelService = new NovelService();
	private NovelClassService novelClassService = new NovelClassService();
	private int novelId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.novel_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看小说详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_novelId = (TextView) findViewById(R.id.TV_novelId);
		TV_novelClassObj = (TextView) findViewById(R.id.TV_novelClassObj);
		TV_novelName = (TextView) findViewById(R.id.TV_novelName);
		iv_novelPhoto = (ImageView) findViewById(R.id.iv_novelPhoto); 
		TV_author = (TextView) findViewById(R.id.TV_author);
		TV_publish = (TextView) findViewById(R.id.TV_publish);
		TV_publishDate = (TextView) findViewById(R.id.TV_publishDate);
		TV_novelPageNum = (TextView) findViewById(R.id.TV_novelPageNum);
		TV_wordsNum = (TextView) findViewById(R.id.TV_wordsNum);
		iv_novelFile = (ImageView) findViewById(R.id.iv_novelFile); 
		TV_tjFlag = (TextView) findViewById(R.id.TV_tjFlag);
		TV_readCount = (TextView) findViewById(R.id.TV_readCount);
		TV_novelDesc = (TextView) findViewById(R.id.TV_novelDesc);
		Bundle extras = this.getIntent().getExtras();
		novelId = extras.getInt("novelId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NovelDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    novel = novelService.GetNovel(novelId); 
		this.TV_novelId.setText(novel.getNovelId() + "");
		NovelClass novelClassObj = novelClassService.GetNovelClass(novel.getNovelClassObj());
		this.TV_novelClassObj.setText(novelClassObj.getClassName());
		this.TV_novelName.setText(novel.getNovelName());
		byte[] novelPhoto_data = null;
		try {
			// 获取图片数据
			novelPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + novel.getNovelPhoto());
			Bitmap novelPhoto = BitmapFactory.decodeByteArray(novelPhoto_data, 0,novelPhoto_data.length);
			this.iv_novelPhoto.setImageBitmap(novelPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_author.setText(novel.getAuthor());
		this.TV_publish.setText(novel.getPublish());
		Date publishDate = new Date(novel.getPublishDate().getTime());
		String publishDateStr = (publishDate.getYear() + 1900) + "-" + (publishDate.getMonth()+1) + "-" + publishDate.getDate();
		this.TV_publishDate.setText(publishDateStr);
		this.TV_novelPageNum.setText(novel.getNovelPageNum() + "");
		this.TV_wordsNum.setText(novel.getWordsNum() + "");
		byte[] novelFile_data = null;
		try {
			// 获取图片数据
			novelFile_data = ImageService.getImage(HttpUtil.BASE_URL + novel.getNovelFile());
			Bitmap novelFile = BitmapFactory.decodeByteArray(novelFile_data, 0,novelFile_data.length);
			this.iv_novelFile.setImageBitmap(novelFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_tjFlag.setText(novel.getTjFlag());
		this.TV_readCount.setText(novel.getReadCount() + "");
		this.TV_novelDesc.setText(novel.getNovelDesc());
	} 
}
