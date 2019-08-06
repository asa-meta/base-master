package com.asa.meta.basehabit.adapter.image;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes))
                    .into(imageView);
        }
    }

    @BindingAdapter(value = {"drawable", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, Drawable drawable, int placeholderRes) {
        //使用Glide框架加载图片
        Glide.with(imageView.getContext())
                .load(drawable)
                .apply(new RequestOptions().placeholder(placeholderRes))
                .into(imageView);

    }

    @BindingAdapter(value = {"OnSeekBarChangeListener"}, requireAll = false)
    public static void setSeekBarListener(SeekBar seekBar, SeekBar.OnSeekBarChangeListener OnSeekBarChangeListener) {
        seekBar.setOnSeekBarChangeListener(OnSeekBarChangeListener);
    }
}