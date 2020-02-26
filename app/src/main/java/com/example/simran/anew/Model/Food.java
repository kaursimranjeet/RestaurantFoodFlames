package com.example.simran.anew.Model;

public class Food {
    private String Name, Image, Description, Price, MenuId;

    public Food() {}

    public Food(String name, String image, String description, String price, String menuId) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        MenuId = menuId;
    }



    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getDescription() {
        return Description;
    }

    public String getPrice() {
        return Price;
    }

    public String getMenuId() {
        return MenuId;
    }
}
