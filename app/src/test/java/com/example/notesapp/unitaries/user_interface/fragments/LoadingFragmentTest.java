package com.example.notesapp.unitaries.user_interface.fragments;

import androidx.fragment.app.testing.FragmentScenario;

import com.example.notesapp.user_interface.fragments.LoadingFragment;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LoadingFragmentTest {
    @Test
    public void testIfFragmentIsRendered() {
        FragmentScenario<LoadingFragment> fragmentScenario =
                FragmentScenario.launchInContainer(LoadingFragment.class);

        fragmentScenario.onFragment(Assert::assertNotNull);
    }
}