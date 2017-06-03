package com.heinrichreimersoftware.materialintro.view.parallax;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ParallaxSlideFragment extends SlideFragment implements Parallaxable {

    private final List<Parallaxable> parallaxableChildren = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        parallaxableChildren = findParallaxLayouts(view);
    }

    public void findParallaxLayouts(View root) {
        Queue<View> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            View child = queue.remove();
            if (child instanceof Parallaxable) {
                parallaxableChildren.add((Parallaxable) child);
            }
            else if (child instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) child;
                for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
                    queue.add(viewGroup.getChildAt(i));
                }
            }
        }
    }

    @Override
    public void setOffset(@FloatRange(from = -1.0, to = 1.0) float offset) {
        if (!parallaxableChildren.isEmpty()) {
            for (Parallaxable parallaxable : parallaxableChildren) {
                parallaxable.setOffset(offset);
            }
        }
    }
}
