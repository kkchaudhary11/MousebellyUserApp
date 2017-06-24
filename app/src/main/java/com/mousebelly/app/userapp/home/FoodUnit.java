package com.mousebelly.app.userapp.home;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mousebelly.app.userapp.IdManager;
import com.mousebelly.app.userapp.R;

import java.net.URL;


/**
 * Created by Kamal Kant on 03-06-2017.
 */

public class FoodUnit {

    private String _id;
    private String Prod_name;
    private String Prod_category;
    private String Description;
    private String Price;
    private String Prod_id;
    private String feedback;
    private String isApproved;
    private String isRejected;
    private String Image;
    private String starsize;
    private String salePrice;
    private String hwfName;

    private String Type;

    private Bitmap bmpImage;


    //getters and setters

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProd_name() {
        return Prod_name;
    }

    public void setProd_name(String prod_name) {
        Prod_name = prod_name;
    }

    public String getProd_category() {
        return Prod_category;
    }

    public void setProd_category(String prod_category) {
        Prod_category = prod_category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProd_id() {
        return Prod_id;
    }

    public void setProd_id(String prod_id) {
        Prod_id = prod_id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(String isRejected) {
        this.isRejected = isRejected;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;

        try {
            URL url = new URL(image);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            this.bmpImage = bmp;

            System.out.println("Bitmap Loaded");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStarsize() {
        return starsize;
    }

    public void setStarsize(String starsize) {
        this.starsize = starsize;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getHwfName() {
        return hwfName;
    }

    public void setHwfName(String hwfName) {
        this.hwfName = hwfName;
    }

    @Override
    public String toString() {
        return "FoodUnit{" +
                "_id='" + _id + '\'' +
                ", Prod_name='" + Prod_name + '\'' +
                ", Prod_category='" + Prod_category + '\'' +
                ", Description='" + Description + '\'' +
                ", Price='" + Price + '\'' +
                ", Prod_id='" + Prod_id + '\'' +
                ", feedback='" + feedback + '\'' +
                ", isApproved='" + isApproved + '\'' +
                ", isRejected='" + isRejected + '\'' +
                ", Image='" + Image + '\'' +
                ", starsize='" + starsize + '\'' +
                ", salePrice='" + salePrice + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }

    static int Id = 0;
    int myId;

    RelativeLayout rl;
    CardView cards;
    RelativeLayout cardrl;
    ImageView productImage;
    TextView productNameTextView;
    TextView productCategoryView;
    TextView priceTextView;
    TextView hwfTextView;
    ImageView typeImage;
    RatingBar ratingBar;
    TextView descriptionTextView;
    Button addToCart;

    public FoodUnit(){
        Id++;
        this.myId = Id;

        this.rl = new RelativeLayout(Home.homeContext);
        IdManager.addId("relativeLayout" + this.myId);
        this.rl.setId((int) IdManager.stringToIdMap.get("relativeLayout" + this.myId));

        this.cards = new CardView(Home.homeContext);
        IdManager.addId("Productlistcards" + this.Id);
        this.cards.setId((Integer) IdManager.stringToIdMap.get("Productlistcards" + this.Id));

        this.productImage = new ImageView(Home.homeContext);
        IdManager.addId("productImage" + this.Id);
        this.productImage.setId((Integer) IdManager.stringToIdMap.get("productImage" + this.Id));

        this.productNameTextView = new TextView(Home.homeContext);
        IdManager.addId("productNameTextView" + this.myId);
        this.productNameTextView.setId((int) IdManager.stringToIdMap.get("productNameTextView" + this.myId));

        this.productCategoryView = new TextView(Home.homeContext);
        IdManager.addId("productCategoryView" + this.myId);
        this.productCategoryView.setId((int) IdManager.stringToIdMap.get("productCategoryView" + this.myId));

        this.cardrl = new RelativeLayout(Home.homeContext);
        IdManager.addId("cardrl" + this.myId);
        this.cardrl.setId((int) IdManager.stringToIdMap.get("cardrl" + this.myId));

        this.priceTextView = new TextView(Home.homeContext);
        IdManager.addId("priceTextView" + this.myId);
        this.priceTextView.setId((int) IdManager.stringToIdMap.get("priceTextView" + this.myId));

        this.ratingBar = new RatingBar(Home.homeContext);
        IdManager.addId("ratingBar" + this.myId);
        this.ratingBar.setId((int) IdManager.stringToIdMap.get("ratingBar" + this.myId));

        this.hwfTextView = new TextView(Home.homeContext);
        IdManager.addId("hwfTextView" + this.myId);
        this.hwfTextView.setId((int) IdManager.stringToIdMap.get("hwfTextView" + this.myId));

        this.typeImage = new ImageView(Home.homeContext);
        IdManager.addId("typeImage" + this.myId);
        this.typeImage.setId((int) IdManager.stringToIdMap.get("typeImage" + this.myId));

        this.descriptionTextView = new TextView(Home.homeContext);
        IdManager.addId("descriptionTextView" + this.myId);
        this.descriptionTextView.setId((int) IdManager.stringToIdMap.get("descriptionTextView" + this.myId));
        this.descriptionTextView.setVisibility(View.INVISIBLE);

        this.addToCart = new Button(Home.homeContext);
        IdManager.addId("addToCart" + this.myId);
        this.addToCart.setId((int) IdManager.stringToIdMap.get("addToCart" + this.myId));

    }

    public RelativeLayout draw(final FoodUnit f){

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); ;

        //cart view

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.cards.setLayoutParams(params);
        this.cards.setContentPadding(0, 0, 0, 0);
        this.cards.setCardElevation(15);
        this.cards.setMaxCardElevation(20);
        this.cards.setUseCompatPadding(true);
        this.cards.setCardBackgroundColor(Color.parseColor("#EEEEEE"));

        //image view
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 500);
        this.productImage.setLayoutParams(params);
        this.productImage.setAdjustViewBounds(true);
        this.productImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.productImage.setPadding(10, 10, 10, 10);
        this.productImage.setImageBitmap(f.bmpImage);
        this.cardrl.addView(productImage);

        /*this.productImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    descriptionTextView.setVisibility(View.VISIBLE);
                    productImage.setAlpha(150);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    descriptionTextView.setVisibility(View.INVISIBLE);
                    productImage.setAlpha(100);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    descriptionTextView.setVisibility(View.INVISIBLE);
                    productImage.setAlpha(100);
                }
                return true;
            }
        });*/
        //description name
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //params.addRule(RelativeLayout.BELOW,productImage.getId());
        this.descriptionTextView.setLayoutParams(params);
        this.descriptionTextView.setPadding(10, 10, 10, 0);
        this.descriptionTextView.setTextSize(21);
        this.descriptionTextView.setTextColor(Color.WHITE);
        this.descriptionTextView.setText(f.getProd_name());
        this.cardrl.addView(descriptionTextView);


        //product name
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,productImage.getId());
        this.productNameTextView.setLayoutParams(params);
        this.productNameTextView.setPadding(20, 10, 10, 0);
        this.productNameTextView.setTextSize(21);
        this.productNameTextView.setText(f.getProd_name());
        this.cardrl.addView(productNameTextView);

        //product type
        params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.BELOW,productImage.getId());
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        this.typeImage.setLayoutParams(params);
        this.typeImage.setPadding(10,10,10,10);
        if(f.getType()!=null) {
            if (f.getType().equals("VEG")) {
                this.typeImage.setImageResource(R.drawable.veg_food_icon);
            }else {
                this.typeImage.setImageResource(R.drawable.non_veg_food_icon);
            }
        }
        this.cardrl.addView(typeImage);

        //category name
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,productNameTextView.getId());
        this.productCategoryView.setLayoutParams(params);
        this.productCategoryView.setPadding(20, 0, 20, 10);
        this.productCategoryView.setTypeface(null, Typeface.BOLD);
        this.productCategoryView.setTextColor(ContextCompat.getColor(Home.homeContext, R.color.Affair));
        this.productCategoryView.setText(f.getProd_category());
        this.cardrl.addView(productCategoryView);

        //price view
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,productCategoryView.getId());
        this.priceTextView.setLayoutParams(params);
        this.priceTextView.setPadding(30, 0, 50, 10);
        this.priceTextView.setTextSize(20);
        //this.priceTextView.setTypeface(null, Typeface.BOLD);
        this.priceTextView.setTextColor(ContextCompat.getColor(Home.homeContext, R.color.Roman));
        this.priceTextView.setText("₹"+f.getPrice());
        this.cardrl.addView(priceTextView);

        //hwf view
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF,productCategoryView.getId());
        params.addRule(RelativeLayout.BELOW,productNameTextView.getId());
        this.hwfTextView.setLayoutParams(params);
        this.hwfTextView.setPadding(20, 0, 20, 0);
        this.hwfTextView.setTextColor(ContextCompat.getColor(Home.homeContext, R.color.Amulet));
        this.hwfTextView.setText(f.getHwfName());
        this.hwfTextView.setBackgroundResource(R.drawable.customborder);
        this.cardrl.addView(hwfTextView);


        //rating
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,productNameTextView.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        this.ratingBar.setLayoutParams(params);
        this.ratingBar.setPadding(0,40,0,0);
        this.ratingBar.setStepSize((float) 0.1);
        this.ratingBar.setNumStars(5);
        this.ratingBar.setScaleX(0.5f);
        this.ratingBar.setScaleY(0.5f);
        this.ratingBar.setIsIndicator(true);
        this.ratingBar.setRating(Float.parseFloat(f.starsize));
        this.cardrl.addView(ratingBar);
        //this.prodRatingbar.setBackgroundColor(Color.parseColor("#FFFF00"));

        //buy button
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.BELOW,typeImage.getId());
        this.addToCart.setLayoutParams(params);
        this.addToCart.setPadding(20, 10, 20, 0);
        this.addToCart.setText("Buy");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.addToCart.setBackgroundTintList(Home.homeContext.getResources().getColorStateList(R.color.button_tint));
        }
        this.cardrl.addView(addToCart);

        this.cards.addView(this.cardrl);

        rl.addView(this.cards);

        return rl;
    }


}
