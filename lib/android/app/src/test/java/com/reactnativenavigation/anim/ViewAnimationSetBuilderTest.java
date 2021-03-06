package com.reactnativenavigation.anim;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.reactnativenavigation.BaseTest;

import org.junit.Test;
import org.robolectric.shadow.api.Shadow;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ViewAnimationSetBuilderTest extends BaseTest {

	private Runnable mockListener;

	@Override
	public void beforeEach() {
		super.beforeEach();
		mockListener = mock(Runnable.class);
	}

	@Test
	public void implementsViewAnimationListener() throws Exception {
		assertThat(new ViewAnimationSetBuilder()).isInstanceOf(Animation.AnimationListener.class);
	}

	@Test
	public void optionalCompletionListener() throws Exception {
		new ViewAnimationSetBuilder()
				.add(someView(), someAnimation())
				.start();
		verify(mockListener, times(0)).run();
	}

	@Test
	public void startsAllAnimations() throws Exception {
		Animation anim1 = someAnimation();
		Animation anim2 = someAnimation();
		new ViewAnimationSetBuilder()
				.withEndListener(mockListener)
				.add(someView(), anim1)
				.add(someView(), anim2)
				.start();
		assertThat(anim1.hasStarted()).isTrue();
		assertThat(anim2.hasStarted()).isTrue();
	}

	@Test
	public void callsEndListenerOnlyAfterAllAnimationsFinish() throws Exception {
		Animation anim1 = someAnimation();
		Animation anim2 = someAnimation();
		ViewAnimationSetBuilder uut = new ViewAnimationSetBuilder();
		uut.withEndListener(mockListener)
				.add(someView(), anim1)
				.add(someView(), anim2)
				.start();
		verify(mockListener, times(1)).run();
	}

	@Test
	public void clearsAnimationFromViewsAfterFinished() throws Exception {
		View v1 = someView();
		View v2 = someView();
		new ViewAnimationSetBuilder()
				.withEndListener(mockListener)
				.add(v1, someAnimation())
				.start();
		assertThat(v1.getAnimation()).isNull();
		assertThat(v2.getAnimation()).isNull();
	}

	private Animation someAnimation() {
		return Shadow.newInstanceOf(AlphaAnimation.class);
	}

	private View someView() {
		return Shadow.newInstanceOf(View.class);
	}
}
