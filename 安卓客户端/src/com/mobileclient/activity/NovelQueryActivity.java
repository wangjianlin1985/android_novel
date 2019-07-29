package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Novel;
import com.mobileclient.domain.NovelClass;
import com.mobileclient.service.NovelClassService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class NovelQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明小说类别下拉框
	private Spinner spinner_novelClassObj;
	private ArrayAdapter<String> novelClassObj_adapter;
	private static  String[] novelClassObj_ShowText  = null;
	private List<NovelClass> novelClassList = null; 
	/*小说类别管理业务逻辑层*/
	private NovelClassService novelClassService = new NovelClassService();
	// 声明小说名称输入框
	private EditText ET_novelName;
	// 声明作者输入框
	private EditText ET_author;
	// 声明出版社输入框
	private EditText ET_publish;
	// 出版日期控件
	private DatePicker dp_publishDate;
	private CheckBox cb_publishDate;
	// 声明是否推荐输入框
	private EditText ET_tjFlag;
	/*查询过滤条件保存到这个对象中*/
	private Novel queryConditionNovel = new Novel();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.novel_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置小说查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_novelClassObj = (Spinner) findViewById(R.id.Spinner_novelClassObj);
		// 获取所有的小说类别
		try {
			novelClassList = novelClassService.QueryNovelClass(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int novelClassCount = novelClassList.size();
		novelClassObj_ShowText = new String[novelClassCount+1];
		novelClassObj_ShowText[0] = "不限制";
		for(int i=1;i<=novelClassCount;i++) { 
			novelClassObj_ShowText[i] = novelClassList.get(i-1).getClassName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		novelClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, novelClassObj_ShowText);
		// 设置小说类别下拉列表的风格
		novelClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_novelClassObj.setAdapter(novelClassObj_adapter);
		// 添加事件Spinner事件监听
		spinner_novelClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionNovel.setNovelClassObj(novelClassList.get(arg2-1).getClassId()); 
				else
					queryConditionNovel.setNovelClassObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_novelClassObj.setVisibility(View.VISIBLE);
		ET_novelName = (EditText) findViewById(R.id.ET_novelName);
		ET_author = (EditText) findViewById(R.id.ET_author);
		ET_publish = (EditText) findViewById(R.id.ET_publish);
		dp_publishDate = (DatePicker) findViewById(R.id.dp_publishDate);
		cb_publishDate = (CheckBox) findViewById(R.id.cb_publishDate);
		ET_tjFlag = (EditText) findViewById(R.id.ET_tjFlag);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionNovel.setNovelName(ET_novelName.getText().toString());
					queryConditionNovel.setAuthor(ET_author.getText().toString());
					queryConditionNovel.setPublish(ET_publish.getText().toString());
					if(cb_publishDate.isChecked()) {
						/*获取出版日期*/
						Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
						queryConditionNovel.setPublishDate(new Timestamp(publishDate.getTime()));
					} else {
						queryConditionNovel.setPublishDate(null);
					} 
					queryConditionNovel.setTjFlag(ET_tjFlag.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionNovel", queryConditionNovel);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
