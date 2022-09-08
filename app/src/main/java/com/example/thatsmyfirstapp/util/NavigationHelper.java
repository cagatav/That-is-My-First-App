package com.example.thatsmyfirstapp.util;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.thatsmyfirstapp.AppManager;
import com.example.thatsmyfirstapp.R;
import com.example.thatsmyfirstapp.fragment.LoginFragment;

public class NavigationHelper {

    private static final NavRedirect redirectState = null;
    public static FragmentManager manager;
    public static String redirectPid;
    public static String redirectDid;
    public static Fragment lastFragment;
    public static NavType lastType;
    public static int size;
    private static double hideDelay;
    private static boolean diffState;

    // Fragment Navigation

    public static void setManager(FragmentManager manager) {
        NavigationHelper.manager = manager;

    }

    /**
     * Fragment navigation method
     *
     * @param fragment
     * @param type
     * @param stack
     * @param anim
     */
    public static void navigate(Fragment fragment, NavType type, NavStack stack, NavAnim anim) {
        UtilHelper.hideKeyboard(AppManager.getActivity());
        size = manager.getBackStackEntryCount();
        lastType = type;

        FragmentTransaction navigationTransaction = manager.beginTransaction();

        lastFragment = NavigationHelper.getLast();

        if (lastFragment != null)
            lastFragment.onPause();

        int[] customAnimation = getAnimation(anim);

        if (customAnimation.length == 2) {
            navigationTransaction.setCustomAnimations(
                    customAnimation[0],
                    customAnimation[1]
            );
        } else if (customAnimation.length == 4) {
            navigationTransaction.setCustomAnimations(
                    customAnimation[0],
                    customAnimation[1],
                    customAnimation[2],
                    customAnimation[3]
            );
        }


        try {
            if (type == NavType.Add)
                navigationTransaction.add(R.id.fragment_container, fragment);
            else
                navigationTransaction.replace(R.id.fragment_container, fragment);

            if (stack != null)
                navigationTransaction.addToBackStack(stack.name());

            navigationTransaction.commitAllowingStateLoss();
        } catch (NullPointerException e) {
            Log.d("NullPointerException", "navigate: ");
        }
    }

    public static void navigate(Fragment fragment, NavType type, NavStack stack, NavAnim anim, int value) {
        UtilHelper.hideKeyboard(AppManager.getActivity());
        size = manager.getBackStackEntryCount();
        lastType = type;

        FragmentTransaction navigationTransaction = manager.beginTransaction();

        lastFragment = NavigationHelper.getLast();

        if (lastFragment != null)
            lastFragment.onPause();

        int[] customAnimation = getAnimation(anim);

        if (customAnimation.length == 2) {
            navigationTransaction.setCustomAnimations(
                    customAnimation[0],
                    customAnimation[1]
            );
        } else if (customAnimation.length == 4) {
            navigationTransaction.setCustomAnimations(
                    customAnimation[0],
                    customAnimation[1],
                    customAnimation[2],
                    customAnimation[3]
            );
        }


        try {
            if (type == NavType.Add)
                navigationTransaction.add(value, fragment);
            else
                navigationTransaction.replace(value, fragment);

            if (stack != null)
                navigationTransaction.addToBackStack(stack.name());

            navigationTransaction.commitAllowingStateLoss();
        } catch (NullPointerException e) {
            Log.d("NullPointerException", "navigate: ");
        }
    }

    /**
     * Get navigation animation according to {@link NavAnim} type
     *
     * @param anim
     */
    private static int[] getAnimation(NavAnim anim) {

        switch (anim) {
            case Nothing:
                return new int[]{};
            case Slide:
                return new int[]{
                        R.anim.slide_in_right,//enter
                        R.anim.slide_out_left,//exit
                        R.anim.slide_in_left,//popEnter
                        R.anim.slide_out_right//popExit
                };
            case SlideReverse:
                return new int[]{
                        R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                };
            case Fade:
                return new int[]{
                        R.anim.fade_in,
                        R.anim.fade_out
                };
            case Dialog:
                return new int[]{
                        R.anim.slide_in_down,
                        R.anim.slide_out_up,
                        R.anim.slide_in_up,
                        R.anim.slide_out_down
                };
        }


        return new int[]{};
    }

    /**
     * Getting last page
     */
    public static Fragment getLast() {
        return manager.findFragmentById(R.id.fragment_container);
    }

    /**
     * Checks if given fragment class is available on navigation stack
     *
     * @param searchingClass
     */
    public static <T extends Fragment> boolean isAvailable(Class<T> searchingClass) {
        for (Fragment f : manager.getFragments()) {
            if (f.getClass() == searchingClass)
                return true;
        }

        return false;
    }

    /**
     * Pops navigation stack according to given fragment class
     *
     * @param searchingClass
     * @param resumeState
     */
    public static <T extends Fragment> void popTo(Class<T> searchingClass, boolean resumeState) {
        while (getLast().getClass() != searchingClass) {
            try {
                manager.popBackStackImmediate();
            } catch (IllegalStateException e) {

            }
        }

        showLast(resumeState);
    }

    /**
     * Checks if given fragment class is the last fragment in the navigation stack
     *
     * @param searchingClass
     * @param <T>
     * @return
     */
    public static <T extends Fragment> boolean checkLast(Class<T> searchingClass) {
        return getLast().getClass() == searchingClass;
    }

    /**
     * Sends data to current fragment
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void postProcess(int requestCode, int resultCode, Intent data) {
        Fragment lastFragment = getLast();

        if (lastFragment != null)
            lastFragment.onActivityResult(requestCode, resultCode, data);
    }

    public static void removeStack(NavStack stack) {
        try {
            manager.popBackStackImmediate(stack.name(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.executePendingTransactions();
        } catch (IllegalStateException e) {
        }
    }

    /**
     * Removes last fragment from the navigation stack
     */
    public static void removeLast() {
        try {
            if (manager.popBackStackImmediate())
                showLast(true);
        } catch (IllegalStateException e) {

        }
    }

    /**
     * Shows last fragment in the navigation stack
     *
     * @param state
     */
    public static void showLast(boolean state) {
        Fragment lastFragment = getLast();

        if (lastFragment != null) {

            FragmentTransaction showTransaction = manager.beginTransaction();

            showTransaction.show(lastFragment);
            showTransaction.commit();

            if (state)
                lastFragment.onResume();
        }
    }

    /**
     * Clears the navigation stack
     */
    public static void removeAll() {
        while (manager.getBackStackEntryCount() != 0) {
            try {
                manager.popBackStackImmediate();
            } catch (IllegalStateException e) {
            }
        }
    }

    public static void navigateLogin() {
        removeAll();

        LoginFragment fragment = new LoginFragment();
        navigate(fragment, NavType.Add, NavStack.PreLogin, NavAnim.SlideReverse);
    }


    /**
     * Navigates to app settings page
     *
     * @param context
     */
    public static void navigateAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left).toBundle();

        ContextCompat.startActivity(context, intent, bundle);
    }

    /**
     * Navigates to given application with specific
     * Navigates to browser if given application with given package name is not installed
     *
     * @param context
     * @param link
     * @param packageName
     */
    public static void navigateApp(Context context, String link, String packageName) {
        Uri uri = Uri.parse(link);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(packageName);

        try {
            context.startActivity(intent, ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left).toBundle());
        } catch (ActivityNotFoundException exception) {
            //WebHelper.launchUrl(context, link);
        }
    }

    /**
     * Navigates to application page on Google Play Store
     * Navigates to browser if Google Play Store is not installed
     *
     * @param context
     */
    public static void navigateAppPage(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public enum NavType {
        Add,
        Replace
    }

    public enum NavStack {
        DisAllow,
        Empty,
        PreLogin,
        NoProperty,
        Detail,
        DevicesDetail,
        Settings,
        AllBoats,
        BoatSettings,
        Devices,
        BoatAdd,
        CodeScan,
        CodeManual,
        CodeHelp,
        DeviceSetupFirst,
        DeviceSetup,
        Subscription,
        SubscriptionIntro
    }

    public enum NavAnim {
        Nothing,
        Slide,
        SlideReverse,
        Fade,
        Dialog
    }

    public enum NavRedirect {
        Login,
        Detail,
        Settings,
        Users,
        Subscription
    }

}