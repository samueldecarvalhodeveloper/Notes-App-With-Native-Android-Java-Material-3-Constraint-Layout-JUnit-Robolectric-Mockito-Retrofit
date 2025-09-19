package com.example.notesapp.unitaries.user_interface.fragments;

import androidx.fragment.app.testing.FragmentScenario;

import com.example.notesapp.user_interface.fragments.NoNoteFragment;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NoNoteFragmentTest {
    @Test
    public void testIfFragmentIsRendered() {
        FragmentScenario<NoNoteFragment> fragmentScenario =
                FragmentScenario.launchInContainer(NoNoteFragment.class);

        fragmentScenario.onFragment(Assert::assertNotNull);
    }
}