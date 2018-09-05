package com.lank.study;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;

import static java.lang.System.out;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)

public class launchAppInDeffLang {

    public UiDevice device;
    private Instrumentation instrumentation;
    public Bundle bundle;
    public Log log;
    String TAG = "Testlog";
    int i;
    String s;
    String packagename="";

    @Before
    public void before() throws IOException, UiObjectNotFoundException {
        // Initialize UiDevice instance
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        bundle = InstrumentationRegistry.getArguments();
        //assertThat(device, notNullValue());

        File regionList;
        File languageList;
        File testResult;
        File tempFile;

    }

    @Test
    public void langTest() throws UiObjectNotFoundException, InterruptedException, IOException {
        device.pressHome();
        device.executeShellCommand("am start -n com.android.settings/com.android.settings.Settings");
        device.waitForWindowUpdate("", 3000);
        UiScrollable settingsViews = new UiScrollable(new UiSelector().scrollable(true));
        settingsViews.setAsVerticalList();
        UiObject langItem = settingsViews.getChildByText(new UiSelector().text("System"), "System");
        langItem.clickAndWaitForNewWindow();
        UiObject2 langInput = device.findObject(By.text("Languages & input"));
        langInput.click();
        sleep(2000);
        UiObject2 langPref = device.findObject(By.text("Language preferences"));
        langPref.click();
        sleep(2000);
        UiObject2 addlang = device.findObject(By.res("com.android.settings:id/add_language"));
        addlang.click();
        sleep(2000);
        UiCollection langs = new UiCollection(new UiSelector().className("android.widget.ListView"));
        int langCount = langs.getChildCount(new UiSelector().resourceId("com.android.settings:id/locale"));
        System.out.print(langCount);
        for (i = 0; i <= langCount; i++) {
            UiObject langIt = langs.getChild(new UiSelector().resourceId("com.android.settings:id/locale").index(i));
            if (langIt.exists()) {
                s = langIt.getText();
                Log.i(TAG, "langname is: " + s);
            }
        }
        Log.i(TAG, "langCount: " + langCount);

        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.asus.camera");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        Log.i(TAG, "activityName: " + intent);


    }

    public void sleep(int mint){
        try {
            Thread.sleep(mint);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void log(String info){
        Bundle bundle = new Bundle();
        bundle.clear();
        bundle.putString("System.out"," "+info);
        instrumentation.sendStatus(0,bundle);
    }

}

