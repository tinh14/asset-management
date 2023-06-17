/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author tinhlam
 */
public class AssetModel {

    private int id;
    private String name;
    private int weight;
    private String unit;
    private int price;
    private int percentageDepreciation;
    private int quantity;
    private int total;
    private String image;
    private AssetTypeModel assetType;

    public AssetModel() {
    }
    
    public AssetModel(int id){
        this.id = id;
    }

    @JsonCreator
    public AssetModel(@JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("weight") int weight,
            @JsonProperty("unit") String unit,
            @JsonProperty("price") int price,
            @JsonProperty("percentageDepreciation") int percentageDepreciation,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("total") int total,
            @JsonProperty("image") String image,
            @JsonProperty("assetTypeId") int assetTypeId) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.unit = unit;
        this.price = price;
        this.percentageDepreciation = percentageDepreciation;
        this.quantity = quantity;
        this.total = total;
        this.image = image;
        this.assetType = new AssetTypeModel();
        this.assetType.setId(assetTypeId);
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the percentageDepreciation
     */
    public int getPercentageDepreciation() {
        return percentageDepreciation;
    }

    /**
     * @param percentageDepreciation the percentageDepreciation to set
     */
    public void setPercentageDepreciation(int percentageDepreciation) {
        this.percentageDepreciation = percentageDepreciation;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the assetType
     */
    public AssetTypeModel getAssetType() {
        return assetType;
    }

    /**
     * @param assetType the assetType to set
     */
    public void setAssetType(AssetTypeModel assetType) {
        this.assetType = assetType;
    }
    
    public static List<String> getVietnameseAttributeName(){
        return new ArrayList<>(Arrays.asList("Mã tài sản", "Tên tài sản", "Khối lượng", "Đơn vị tính", "Giá", "Số lượng", "Hình ảnh", "Loại tài sản"));
    }
}
