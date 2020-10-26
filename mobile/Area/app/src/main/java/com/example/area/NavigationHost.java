package com.example.area;

import androidx.fragment.app.Fragment;

/**
 * NavigationHost, interface allowing to navigate between fragments
 */
public interface NavigationHost {
    /**
     * Trigger a navigation to the specified fragment, optionally adding a transaction to the back
     * stack to make this navigation reversible.
     */
    void navigateTo(Fragment fragment, boolean addToBackstack);
}
