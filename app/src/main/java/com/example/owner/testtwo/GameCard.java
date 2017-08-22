package com.example.owner.testtwo;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Owner on 7/30/2017.
 */

public class GameCard extends LinearLayout {
    private ImageView apperence;
    private TextView textViewDamage, textViewDefense, textViewHealth, textDescription, textName;
    private double attackDamage, defensePoints, healthPoints;
    private String name, description;
    private Context contextIn = null;
    private View view = null;
    private String damageLabel;

    public GameCard(Context context) {
        super(context);
        contextIn = context;
    }

    public GameCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        contextIn = context;
        init(context, attrs);
    }

    public GameCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        contextIn = context;
        init(context, attrs);
    }

    public GameCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        contextIn = context;
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {

        TypedArray gameCardArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GameCard, 0, 0);

        try{
            damageLabel = gameCardArray.getString(R.styleable.GameCard_damageLabel);
        }finally {
            gameCardArray.recycle();
        }

        LayoutInflater.from(context).inflate(R.layout.game_card_layout, this);

        textViewDamage = (TextView) this.findViewById(R.id.textViewDamage);
        textViewDamage.setText(damageLabel);
    }

    public String getDamageLabel(){
        return damageLabel;
    }

    public void setDamageLabel(String damageLabelSet){
        this.damageLabel = damageLabelSet;
        if(textViewDamage != null){
            textViewDamage.setText(damageLabel);
        }
    }
}
