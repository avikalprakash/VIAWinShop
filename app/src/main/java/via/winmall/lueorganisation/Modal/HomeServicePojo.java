package via.winmall.lueorganisation.Modal;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeServicePojo  {
    String product_id;
    String product_code;
    String product_title;
    String brand;
    String product_price;
    String product_sp_price;
    String product_desc;
    String product_image;

    public HomeServicePojo(){

    }


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_sp_price() {
        return product_sp_price;
    }

    public void setProduct_sp_price(String product_sp_price) {
        this.product_sp_price = product_sp_price;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
