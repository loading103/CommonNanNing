package com.daqsoft.commonnanning.ui.robot;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.daqsoft.commonnanning.R;

/**
 * 跑马灯View
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018/8/15
 * @since JDK 1.8
 */
public class MarqueeView extends HorizontalScrollView implements Runnable{

  private Context context;
  /**
   * 跑马灯滚动部分
    */
  private LinearLayout mainLayout;
  /**
   * 滚动速度
    */
  private int scrollSpeed = 5;
  /**
   * 滚动方向
   */
  private int scrollDirection = LEFT_TO_RIGHT;
  /**
   * 当前x坐标
   */
  private int currentX;
  /**
   * View间距
   */
  private int viewMargin = 20;
  /**
   * View总宽度
   */
  private int viewWidth;
  /**
   * 屏幕宽度
   */
  private int screenWidth;

  public static final int LEFT_TO_RIGHT = 1;
  public static final int RIGHT_TO_LEFT = 2;

  public MarqueeView(Context context) {
    this(context, null);
  }

  public MarqueeView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MarqueeView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.context = context;
    initView();
  }

  void initView() {
    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    screenWidth = wm.getDefaultDisplay().getWidth();
    mainLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.scroll_content, null);
    this.addView(mainLayout);
  }

  public void addViewInQueue(View view){
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);
    lp.setMargins(viewMargin, 0, 0, 0);
    view.setLayoutParams(lp);
    mainLayout.addView(view);
    view.measure(0, 0);//测量view
    viewWidth = viewWidth + view.getMeasuredWidth() + viewMargin;
  }

  // 开始滚动
  public void startScroll(){
    removeCallbacks(this);
    currentX = (scrollDirection == LEFT_TO_RIGHT ? viewWidth : -screenWidth);
    post(this);
  }

  // 停止滚动
  public void stopScroll(){
    removeCallbacks(this);
  }

  // 设置View间距
  public void setViewMargin(int viewMargin){
    this.viewMargin = viewMargin;
  }

  // 设置滚动速度
  public void setScrollSpeed(int scrollSpeed){
    this.scrollSpeed = scrollSpeed;
  }

  // 设置滚动方向 默认从左向右
  public void setScrollDirection(int scrollDirection){
    this.scrollDirection = scrollDirection;
  }

  @Override
  public void run() {
    switch (scrollDirection){
      case LEFT_TO_RIGHT:
        mainLayout.scrollTo(currentX, 0);
        currentX --;

        if (-currentX >= screenWidth) {
          mainLayout.scrollTo(viewWidth, 0);
          currentX = viewWidth;
        }
        break;
      case RIGHT_TO_LEFT:
        mainLayout.scrollTo(currentX, 0);
        currentX ++;

        if (currentX >= viewWidth) {
          mainLayout.scrollTo(-screenWidth, 0);
          currentX = -screenWidth;
        }
        break;
      default:
        break;
    }

    postDelayed(this, 50 / scrollSpeed);
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    return false;
  }
}