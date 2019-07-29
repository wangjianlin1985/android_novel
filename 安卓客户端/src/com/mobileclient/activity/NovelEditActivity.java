package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Novel;
import com.mobileclient.service.NovelService;
import com.mobileclient.domain.NovelClass;
import com.mobileclient.service.NovelClassService;
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

public class NovelEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明小说idTextView
	private TextView TV_novelId;
	// 声明小说类别下拉框
	private Spinner spinner_novelClassObj;
	private ArrayAdapter<String> novelClassObj_adapter;
	private static  String[] novelClassObj_ShowText  = null;
	private List<NovelClass> novelClassList = null;
	/*小说类别管理业务逻辑层*/
	private NovelClassService novelClassService = new NovelClassService();
	// 声明小说名称输入框
	private EditText ET_novelName;
	// 声明小说图片图片框控件
	private ImageView iv_novelPhoto;
	private Button btn_novelPhoto;
	protected int REQ_CODE_SELECT_IMAGE_novelPhoto = 1;
	private int REQ_CODE_CAMERA_novelPhoto = 2;
	// 声明作者输入框
	private EditText ET_author;
	// 声明出版社输入框
	private EditText ET_publish;
	// 出版出版日期控件
	private DatePicker dp_publishDate;
	// 声明页数输入框
	private EditText ET_novelPageNum;
	// 声明字数输入框
	private EditText ET_wordsNum;
	// 声明小说文件图片框控件
	private ImageView iv_novelFile;
	private Button btn_novelFile;
	protected int REQ_CODE_SELECT_IMAGE_novelFile = 3;
	private int REQ_CODE_CAMERA_novelFile = 4;
	// 声明是否推荐输入框
	private EditText ET_tjFlag;
	// 声明阅读量输入框
	private EditText ET_readCount;
	// 声明小说简介输入框
	private EditText ET_novelDesc;
	protected String carmera_path;
	/*要保存的小说信息*/
	Novel novel = new Novel();
	/*小说管理业务逻辑层*/
	private NovelService novelService = new NovelService();

	private int novelId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.novel_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑小说信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_novelId = (TextView) findViewById(R.id.TV_novelId);
		spinner_novelClassObj = (Spinner) findViewById(R.id.Spinner_novelClassObj);
		// 获取所有的小说类别
		try {
			novelClassList = novelClassService.QueryNovelClass(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int novelClassCount = novelClassList.size();
		novelClassObj_ShowText = new String[novelClassCount];
		for(int i=0;i<novelClassCount;i++) { 
			novelClassObj_ShowText[i] = novelClassList.get(i).getClassName();
		}
		// 将可选内容与ArrayAdapter连接起来
		novelClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, novelClassObj_ShowText);
		// 设置图书类别下拉列表的风格
		novelClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_novelClassObj.setAdapter(novelClassObj_adapter);
		// 添加事件Spinner事件监听
		spinner_novelClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				novel.setNovelClassObj(novelClassList.get(arg2).getClassId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_novelClassObj.setVisibility(View.VISIBLE);
		ET_novelName = (EditText) findViewById(R.id.ET_novelName);
		iv_novelPhoto = (ImageView) findViewById(R.id.iv_novelPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_novelPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(NovelEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_novelPhoto);
			}
		});
		btn_novelPhoto = (Button) findViewById(R.id.btn_novelPhoto);
		btn_novelPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_novelPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_novelPhoto);  
			}
		});
		ET_author = (EditText) findViewById(R.id.ET_author);
		ET_publish = (EditText) findViewById(R.id.ET_publish);
		dp_publishDate = (DatePicker)this.findViewById(R.id.dp_publishDate);
		ET_novelPageNum = (EditText) findViewById(R.id.ET_novelPageNum);
		ET_wordsNum = (EditText) findViewById(R.id.ET_wordsNum);
		iv_novelFile = (ImageView) findViewById(R.id.iv_novelFile);
		/*单击图片显示控件时进行图片的选择*/
		iv_novelFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(NovelEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_novelFile);
			}
		});
		btn_novelFile = (Button) findViewById(R.id.btn_novelFile);
		btn_novelFile.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_novelFile.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_novelFile);  
			}
		});
		ET_tjFlag = (EditText) findViewById(R.id.ET_tjFlag);
		ET_readCount = (EditText) findViewById(R.id.ET_readCount);
		ET_novelDesc = (EditText) findViewById(R.id.ET_novelDesc);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		novelId = extras.getInt("novelId");
		/*单击修改小说按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取小说名称*/ 
					if(ET_novelName.getText().toString().equals("")) {
						Toast.makeText(NovelEditActivity.this, "小说名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_novelName.setFocusable(true);
						ET_novelName.requestFocus();
						return;	
					}
					novel.setNovelName(ET_novelName.getText().toString());
					if (!novel.getNovelPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						NovelEditActivity.this.setTitle("正在上传图片，稍等...");
						String novelPhoto = HttpUtil.uploadFile(novel.getNovelPhoto());
						NovelEditActivity.this.setTitle("图片上传完毕！");
						novel.setNovelPhoto(novelPhoto);
					} 
					/*验证获取作者*/ 
					if(ET_author.getText().toString().equals("")) {
						Toast.makeText(NovelEditActivity.this, "作者输入不能为空!", Toast.LENGTH_LONG).show();
						ET_author.setFocusable(true);
						ET_author.requestFocus();
						return;	
					}
					novel.setAuthor(ET_author.getText().toString());
					/*验证获取出版社*/ 
					if(ET_publish.getText().toString().equals("")) {
						Toast.makeText(NovelEditActivity.this, "出版社输入不能为空!", Toast.LENGTH_LONG).show();
						ET_publish.setFocusable(true);
						ET_publish.requestFocus();
						return;	
					}
					novel.setPublish(ET_publish.getText().toString());
					/*获取出版日期*/
					Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
					novel.setPublishDate(new Timestamp(publishDate.getTime()));
					/*验证获取页数*/ 
					if(ET_novelPageNum.getText().toString().equals("")) {
						Toast.makeText(NovelEditActivity.this, "页数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_novelPageNum.setFocusable(true);
						ET_novelPageNum.requestFocus();
						return;	
					}
					novel.setNovelPageNum(Integer.parseInt(ET_novelPageNum.getText().toString()));
					/*验证获取字数*/ 
					if(ET_wordsNum.getText().toString().equals("")) {
						Toast.makeText(NovelEditActivity.this, "字数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_wordsNum.setFocusable(true);
						ET_wordsNum.requestFocus();
						return;	
					}
					novel.setWordsNum(Integer.parseInt(ET_wordsNum.getText().toString()));
					if (!novel.getNovelFile().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						NovelEditActivity.this.setTitle("正在上传图片，稍等...");
						String novelFile = HttpUtil.uploadFile(novel.getNovelFile());
						NovelEditActivity.this.setTitle("图片上传完毕！");
						novel.setNovelFile(novelFile);
					} 
					/*验证获取是否推荐*/ 
					if(ET_tjFlag.getText().toString().equals("")) {
						Toast.makeText(NovelEditActivity.this, "是否推荐输入不能为空!", Toast.LENGTH_LONG).show();
						ET_tjFlag.setFocusable(true);
						ET_tjFlag.requestFocus();
						return;	
					}
					novel.setTjFlag(ET_tjFlag.getText().toString());
					/*验证获取阅读量*/ 
					if(ET_readCount.getText().toString().equals("")) {
						Toast.makeText(NovelEditActivity.this, "阅读量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_readCount.setFocusable(true);
						ET_readCount.requestFocus();
						return;	
					}
					novel.setReadCount(Integer.parseInt(ET_readCount.getText().toString()));
					/*验证获取小说简介*/ 
					if(ET_novelDesc.getText().toString().equals("")) {
						Toast.makeText(NovelEditActivity.this, "小说简介输入不能为空!", Toast.LENGTH_LONG).show();
						ET_novelDesc.setFocusable(true);
						ET_novelDesc.requestFocus();
						return;	
					}
					novel.setNovelDesc(ET_novelDesc.getText().toString());
					/*调用业务逻辑层上传小说信息*/
					NovelEditActivity.this.setTitle("正在更新小说信息，稍等...");
					String result = novelService.UpdateNovel(novel);
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
	    novel = novelService.GetNovel(novelId);
		this.TV_novelId.setText(novelId+"");
		for (int i = 0; i < novelClassList.size(); i++) {
			if (novel.getNovelClassObj() == novelClassList.get(i).getClassId()) {
				this.spinner_novelClassObj.setSelection(i);
				break;
			}
		}
		this.ET_novelName.setText(novel.getNovelName());
		byte[] novelPhoto_data = null;
		try {
			// 获取图片数据
			novelPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + novel.getNovelPhoto());
			Bitmap novelPhoto = BitmapFactory.decodeByteArray(novelPhoto_data, 0, novelPhoto_data.length);
			this.iv_novelPhoto.setImageBitmap(novelPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_author.setText(novel.getAuthor());
		this.ET_publish.setText(novel.getPublish());
		Date publishDate = new Date(novel.getPublishDate().getTime());
		this.dp_publishDate.init(publishDate.getYear() + 1900,publishDate.getMonth(), publishDate.getDate(), null);
		this.ET_novelPageNum.setText(novel.getNovelPageNum() + "");
		this.ET_wordsNum.setText(novel.getWordsNum() + "");
		byte[] novelFile_data = null;
		try {
			// 获取图片数据
			novelFile_data = ImageService.getImage(HttpUtil.BASE_URL + novel.getNovelFile());
			Bitmap novelFile = BitmapFactory.decodeByteArray(novelFile_data, 0, novelFile_data.length);
			this.iv_novelFile.setImageBitmap(novelFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_tjFlag.setText(novel.getTjFlag());
		this.ET_readCount.setText(novel.getReadCount() + "");
		this.ET_novelDesc.setText(novel.getNovelDesc());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_novelPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_novelPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_novelPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_novelPhoto.setImageBitmap(booImageBm);
				this.iv_novelPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.novel.setNovelPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_novelPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_novelPhoto.setImageBitmap(bm); 
				this.iv_novelPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			novel.setNovelPhoto(filename); 
		}
		if (requestCode == REQ_CODE_CAMERA_novelFile  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_novelFile.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_novelFile.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_novelFile.setImageBitmap(booImageBm);
				this.iv_novelFile.setScaleType(ScaleType.FIT_CENTER);
				this.novel.setNovelFile(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_novelFile && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_novelFile.setImageBitmap(bm); 
				this.iv_novelFile.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			novel.setNovelFile(filename); 
		}
	}
}
