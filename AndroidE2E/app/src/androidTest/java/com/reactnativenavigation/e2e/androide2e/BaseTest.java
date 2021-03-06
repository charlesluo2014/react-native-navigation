package com.reactnativenavigation.e2e.androide2e;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public abstract class BaseTest {
	public static final String PACKAGE_NAME = "com.reactnativenavigation.playground";
	public static final long TIMEOUT = 3000;

	@Before
	public void beforeEach() throws Exception {
		device().wakeUp();
		device().setOrientationNatural();
	}

	@After
	public void afterEach() throws Exception {
		device().executeShellCommand("am force-stop " + PACKAGE_NAME);
	}

	public UiDevice device() {
		return UiDevice.getInstance(getInstrumentation());
	}

	public void launchTheApp() throws Exception {
		device().executeShellCommand("am start -n " + PACKAGE_NAME + "/.MainActivity");
		device().waitForIdle();
		acceptOverlayPermissionIfNeeded();
		device().wait(Until.gone(By.textContains("Please wait")), 1000 * 60 * 3);
	}

	public void assertMainShown() {
		assertExists(By.text("React Native Navigation!"));
	}

	public void acceptOverlayPermissionIfNeeded() throws Exception {
		if (isRequestingOverlayPermission()) {
			if (!elementByText("Playground").exists()) {
				scrollToText("Playground");
			}
			elementByText("Playground").click();
			elementByText("Permit drawing over other apps").click();
			device().pressBack();
			device().pressBack();
		}
	}

	private boolean isRequestingOverlayPermission() {
		return device().wait(Until.hasObject(By.pkg("com.android.settings").depth(0)), 300);
	}

	public UiObject elementByText(String text) {
		return device().findObject(new UiSelector().text(text));
	}

	public UiObject elementByTextContains(String text) {
		return device().findObject(new UiSelector().textContains(text));
	}

	public void scrollToText(String txt) throws Exception {
		new UiScrollable(new UiSelector().scrollable(true)).scrollTextIntoView(txt);
	}

	public void assertExists(BySelector selector) {
		assertThat(device().wait(Until.hasObject(selector), TIMEOUT)).withFailMessage("expected %1$s to be visible", selector).isTrue();
		assertThat(device().findObject(selector).getVisibleCenter().x).isPositive().isLessThan(device().getDisplayWidth());
		assertThat(device().findObject(selector).getVisibleCenter().y).isPositive().isLessThan(device().getDisplayHeight());
	}

	public Bitmap captureScreenshot() throws Exception {
		File file = File.createTempFile("tmpE2E", "png");
		device().takeScreenshot(file);
		Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		file.delete();
		return bitmap;
	}

	public void swipeOpenLeftSideMenu() {
		device().swipe(5, 152, 500, 152, 15);
	}
}
