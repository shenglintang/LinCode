package com.example.day04_lv03;


import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

public class ZoomImageView extends ImageView implements OnGlobalLayoutListener, OnScaleGestureListener,OnTouchListener{
	
	private float mInitScale=1.0f ;
	private boolean once;
	public float mMaxScale;
	private float mMidScale ;
	
    private Matrix mScaleMatrix;
    
    
    /**
     * �����û���ָ����ʱ���ŵı���
     */
    private ScaleGestureDetector mScaleGestureDetector;
    
    //------------------�����ƶ�
    /**
     * ��¼��һ�ζ�㴥�ص����� 
     */
    private int mLastPointerCount;
    private float mLastX;
    private float mLastY;
    private int mTouchSlop;//ϵͳ���Ƿ��ƶ�����ֵ
    private boolean isCanDrag;
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;
    
	//---------------------˫���Ŵ�����С
	private GestureDetector mGestureDetector;
	private boolean isAutoScale;

	public ZoomImageView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	public ZoomImageView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mScaleMatrix=new Matrix();
		super.setScaleType(ScaleType.MATRIX);
		mScaleGestureDetector=new ScaleGestureDetector(getContext(), this);
		setOnTouchListener(this);
		mTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
		mGestureDetector=new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				if (isAutoScale == true)
					return true;
				float x=e.getX();
				float y=e.getY();
				if(getScale()<mMidScale)
				{

					ZoomImageView.this.postDelayed(
							new AutoScaleRunnable(mMaxScale, x, y), 10);
					isAutoScale = true;
				}else{
					ZoomImageView.this.postDelayed(
							new AutoScaleRunnable(mInitScale, x, y), 10);
					isAutoScale = true;
				}
				return true;
			}
			
			
		});
	}
	private class AutoScaleRunnable implements Runnable
	{
		static final float BIGGER = 1.07f;
		static final float SMALLER = 0.93f;
		private float mTargetScale;
		private float tmpScale;

		/**
		 * ���ŵ�����
		 */
		private float x;
		private float y;

		/**
		 * ����Ŀ������ֵ������Ŀ��ֵ�뵱ǰֵ���ж�Ӧ�÷Ŵ�����С
		 * 
		 * @param targetScale
		 */
		public AutoScaleRunnable(float targetScale, float x, float y)
		{
			this.mTargetScale = targetScale;
			this.x = x;
			this.y = y;
			if (getScale() < mTargetScale)
			{
				tmpScale = BIGGER;
			} else
			{
				tmpScale = SMALLER;
			}

		}

		@Override
		public void run()
		{
			// ��������
			mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
			checkBorderAndCenterWhenScale();
			setImageMatrix(mScaleMatrix);

			final float currentScale = getScale();
			// ���ֵ�ںϷ���Χ�ڣ���������
			if (((tmpScale > 1f) && (currentScale < mTargetScale))
					|| ((tmpScale < 1f) && (mTargetScale < currentScale)))
			{
				ZoomImageView.this.postDelayed(this, 16);
			} else
			// ����ΪĿ������ű���
			{
				
				final float deltaScale = mTargetScale / currentScale;
				mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
				checkBorderAndCenterWhenScale();
				setImageMatrix(mScaleMatrix);
				isAutoScale = false;
			}

		}
	}
	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}
	
	
	@Override
	public void onGlobalLayout() {
		// TODO Auto-generated method stub
		if (!once)
		{
			int width = getWidth();
			int height = getHeight();
			Drawable d = getDrawable();
			if (d == null)
				return;
			// �õ�ͼƬ�Ŀ�͸�
			int dw = d.getIntrinsicWidth();
			int dh = d.getIntrinsicHeight();
			float scale = 1.0f;
			// ���ͼƬ�Ŀ���߸ߴ�����Ļ������������Ļ�Ŀ���߸�
			if (dw > width && dh <= height)
			{
				scale = width * 1.0f / dw;
			}
			if (dh > height && dw <= width)
			{
				scale = height * 1.0f / dh;
			}
			// �����͸߶�������Ļ�������䰴��������Ӧ��Ļ��С
			if ((dw > width && dh > height)||(dw <width && dh< height))
			{
				scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
			}
			mInitScale = scale;
			mMaxScale=mInitScale*4;
			mMidScale=mInitScale*2;
            //��ͼƬ��ֲ���ؼ�����
			int dx=getWidth()/2-dw/2;
			int dy=getHeight()/2-dh/2;
			mScaleMatrix.postTranslate(dx, dy);
			mScaleMatrix.postScale(mInitScale, mInitScale,width/2,height/2);
			setImageMatrix(mScaleMatrix);
			once = true;
		}
	}
	/**
	 * ��ȡ��ǰͼƬ������ֵ
	 */
	public float getScale(){
		float[] values=new float[9];
		mScaleMatrix.getValues(values);
		return values[Matrix.MSCALE_X];
	}
	
	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		float scale=getScale();
		float scaleFactor=detector.getScaleFactor();
		if(getDrawable()==null){
			return true;
		}
		//���ŷ�Χ����
		if((scale<mMaxScale && scaleFactor>1.0f)||(scale>mInitScale&&scaleFactor<1.0f)){
			
			if(scale*scaleFactor<mInitScale){
				scaleFactor=mInitScale/scale;
			}
			if(scale*scaleFactor>mMaxScale){
				scaleFactor=mMaxScale/scale;
			}
			//����
			mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
			checkBorderAndCenterWhenScale();
			setImageMatrix(mScaleMatrix);
		}
		
		return false;
	}
	//�õ���С��Ŵ�ʱ��ͼƬ���ĵ�����
	private RectF getMatrixRectF(){
		Matrix matrix=mScaleMatrix;
		RectF rectF=new RectF();
		Drawable d=getDrawable();
		if(d!=null){
			rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			matrix.mapRect(rectF);
		}
		return rectF;
	}
	/**
	 * �����ŵ�ʱ����б߽��λ�õĿ���
	 */
	private void checkBorderAndCenterWhenScale() {
		
		RectF rect=getMatrixRectF();
		float deltaX=0;
		float deltaY=0;
		int width=getWidth();
		int height=getHeight();
		//����ʱ���б߽��⣬��ֹ���ְױ�
		if(rect.width()>=width){//ͼƬ��ȴ�����Ļ���
			if(rect.left>0){
				deltaX=-rect.left;
			} 
			if(rect.right<width){
				deltaX=width-rect.right;
			}
		}
		if(rect.height()>=height){//ͼƬ�߶ȴ�����Ļ�߶�
			if(rect.top>0){
				deltaY=-rect.top;
			}
			if(rect.bottom<height){
				deltaY=height-rect.bottom;
			}
		}
		//����߶Ȼ��߿��С�ڿؼ��Ŀ���߸ߣ����������
		if(rect.width()<width){
			deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
		}
		if(rect.height()<height){
			deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
		}
		mScaleMatrix.postTranslate(deltaX,deltaY);
	}
	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		return true;//��������Ϊtrue
	}
	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * �����ƶ�ֱ��д��onTouch��
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(mGestureDetector.onTouchEvent(event)){
			return true;
		}
		
		mScaleGestureDetector.onTouchEvent(event);//�õ������¼�
		
		float x=0;//��㴥�ص����ĵ�X
		float y=0;//��㴥�ص����ĵ�Y
		//�õ���㴥�ص�����
		int pointerCount=event.getPointerCount();
		for(int i=0;i<pointerCount;i++){
			x+=event.getX(i);
			y+=event.getY(i);
		}
		x/=pointerCount;
		y/=pointerCount;
		if(mLastPointerCount!=pointerCount){
			isCanDrag=false;
			mLastX=x;
			mLastY=y;
		}
		mLastPointerCount=pointerCount;
		RectF rectF = getMatrixRectF();
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			if (rectF.width() > getWidth() || rectF.height() > getHeight())
			{
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (rectF.width() > getWidth() || rectF.height() > getHeight())
			{
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			float dx=x-mLastX;
			float dy=y-mLastY;
			
			if(!isCanDrag){
				isCanDrag=isMoveAction(dx,dy);
			}
			if(isCanDrag){
				if(getDrawable()!=null){
					isCheckLeftAndRight=isCheckTopAndBottom=true;
					//������С�ڿؼ���ȣ�����������ƶ�
					if(rectF.width()<getWidth()){
						isCheckLeftAndRight=false;
						dx=0;
					}
					//����߶�С�ڿؼ��߶ȣ������������ƶ�
					if(rectF.height()<getHeight()){
						isCheckTopAndBottom=false;
						dy=0;
					}
					mScaleMatrix.postTranslate(dx, dy);
					checkBorderWhenTranslate();
					setImageMatrix(mScaleMatrix);
				}
			}
			mLastX=x;
			mLastY=y;
		 break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			mLastPointerCount=0;
			break;
		}
		return true;
	}
	//���ƶ�ʱ�����б߽���
	private void checkBorderWhenTranslate() {
		RectF rectF=getMatrixRectF();
		float deltaX=0;
		float deltaY=0;
		int width=getWidth();
		int height=getHeight();
		if(rectF.top>0 && isCheckTopAndBottom){
			deltaY=-rectF.top;
		}
		if(rectF.bottom<height&&isCheckTopAndBottom){
			deltaY=height-rectF.bottom;
		}
		if(rectF.left>0&&isCheckLeftAndRight){
			deltaX=-rectF.left; 
		}
		if(rectF.right<width&&isCheckLeftAndRight){
			deltaX=width-rectF.right;
		}
		mScaleMatrix.postTranslate(deltaX, deltaY);
		
	}
	//�ж��Ƿ�ﵽ�ƶ�����ֵ
	private boolean isMoveAction(float dx, float dy) {
		
		return Math.sqrt(dx*dx+dy*dy)>mTouchSlop;
	}
}
