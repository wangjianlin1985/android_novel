package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.NovelClassService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class NovelSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public NovelSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.novel_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_novelClassObj = (TextView)convertView.findViewById(R.id.tv_novelClassObj);
	  holder.tv_novelName = (TextView)convertView.findViewById(R.id.tv_novelName);
	  holder.iv_novelPhoto = (ImageView)convertView.findViewById(R.id.iv_novelPhoto);
	  holder.tv_author = (TextView)convertView.findViewById(R.id.tv_author);
	  holder.tv_publish = (TextView)convertView.findViewById(R.id.tv_publish);
	  holder.tv_publishDate = (TextView)convertView.findViewById(R.id.tv_publishDate);
	  holder.tv_tjFlag = (TextView)convertView.findViewById(R.id.tv_tjFlag);
	  holder.tv_readCount = (TextView)convertView.findViewById(R.id.tv_readCount);
	  /*设置各个控件的展示内容*/
	  holder.tv_novelClassObj.setText("小说类别：" + (new NovelClassService()).GetNovelClass(Integer.parseInt(mData.get(position).get("novelClassObj").toString())).getClassName());
	  holder.tv_novelName.setText("小说名称：" + mData.get(position).get("novelName").toString());
	  holder.iv_novelPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener novelPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_novelPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("novelPhoto"),novelPhotoLoadListener);  
	  holder.tv_author.setText("作者：" + mData.get(position).get("author").toString());
	  holder.tv_publish.setText("出版社：" + mData.get(position).get("publish").toString());
	  try {holder.tv_publishDate.setText("出版日期：" + mData.get(position).get("publishDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_tjFlag.setText("是否推荐：" + mData.get(position).get("tjFlag").toString());
	  holder.tv_readCount.setText("阅读量：" + mData.get(position).get("readCount").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_novelClassObj;
    	TextView tv_novelName;
    	ImageView iv_novelPhoto;
    	TextView tv_author;
    	TextView tv_publish;
    	TextView tv_publishDate;
    	TextView tv_tjFlag;
    	TextView tv_readCount;
    }
} 
