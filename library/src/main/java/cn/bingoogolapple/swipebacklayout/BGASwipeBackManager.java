/*
 * Copyright 2016 bingoogolapple
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.bingoogolapple.swipebacklayout;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;

import java.util.Stack;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/28 下午11:42
 * 描述:
 */

/*BGASwipeBackManager应该是作为管理着，继承自Application.ActivityLifecycleCallbacks接口
* ，这样就可以管理所有的Activity的所有生命周期方法*/
public class BGASwipeBackManager implements Application.ActivityLifecycleCallbacks {

    //内部创建该类对象
    private static final BGASwipeBackManager sInstance = new BGASwipeBackManager();
    //Activity栈，先入后出
    private Stack<Activity> mActivityStack = new Stack<>();

    public static BGASwipeBackManager getInstance() {
        return sInstance;
    }

    private BGASwipeBackManager() {
    }

    /**
     * 必须在 Application 的 onCreate 方法中调用
     */
    public void init(Application application) {
        //将监听Activity所有生命周期方法的ActivityLifecycleCallbacks对象添加到Application中
        application.registerActivityLifecycleCallbacks(this);
    }

    //ActivityLifecycleCallback接口中的方法——————————————————————————————————
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivityStack.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityStack.remove(activity);
    }

    //ActivityLifecycleCallback接口中的方法——————————————————————————————————

    /**
     * 获取倒数第二个 Activity，也就是倒数第二个插入栈中的
     *
     * @return
     */
    @Nullable
    public Activity getPenultimateActivity() {
        Activity activity = null;
        try {
            if (mActivityStack.size() > 1) {
                activity = mActivityStack.get(mActivityStack.size() - 2);
            }
        } catch (Exception e) {
        }
        return activity;
    }

    /**panel面板滑动，slideOffset取值是从0到1的*/
    public static void onPanelSlide(float slideOffset) {
        try {
            Activity activity = getInstance().getPenultimateActivity();
            if (activity != null) {
                View decorView = activity.getWindow().getDecorView();
                //ViewCompat也就是View,是为了向下兼容的View
                //ViewCompat.setTranslationX()设置此视图相对于左侧位置的水平位置，
                // 第二个参数是相对于左边的距离，这里设置的第二个参数不太懂 TODO
                ViewCompat.setTranslationX(decorView, -(decorView.getMeasuredWidth() / 3.0f) * (1 - slideOffset));
            }
        } catch (Exception e) {
        }
    }

    /*面板关闭*/
    public static void onPanelClosed() {
        try {
            Activity activity = getInstance().getPenultimateActivity();
            if (activity != null) {
                View decorView = activity.getWindow().getDecorView();
                //设置此视图相对于左侧位置的水平距离
                ViewCompat.setTranslationX(decorView, 0);
            }
        } catch (Exception e) {
        }
    }
}