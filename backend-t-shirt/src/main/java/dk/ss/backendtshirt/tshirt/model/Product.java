package dk.ss.backendtshirt.tshirt.model;

import dk.ss.backendtshirt.common.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull(message = "Size is required")
    @Enumerated(EnumType.STRING) // Gemmer f.eks. "XL" i databasen
    @Column(nullable = false)
    private Size size;

    @NotNull(message = "Color is required")
    @Enumerated(EnumType.STRING) // Gemmer f.eks. "BLUE" i databasen
    @Column(nullable = false)
    private Color color;

    @NotNull(message = "Stock quantity is required")
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private Boolean active = true;

    // 1. No-Args Constructor (Påkrævet af JPA/Hibernate)
    public Product() {
    }

    // 2. Enum Constructor (Den "Rene" / Type-sikre)
    public Product(String name, String description, BigDecimal price, String imageUrl,
                   Size size, Color color, Integer stockQuantity, Boolean active) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.size = size;
        this.color = color;
        this.stockQuantity = stockQuantity;
        this.active = active;
    }

    // 3. Denne constructor tillader at skrive "new Product(..., "XL", "Blue", ...)"
    public Product(String name, String description, BigDecimal price, String imageUrl,
                   String sizeStr, String colorStr, Integer stockQuantity, Boolean active) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stockQuantity = stockQuantity;
        this.active = active;

        // Her sker magien: Vi oversætter String til Enum
        // Oversætter Strings til Enums via hjælpe-metoderne
        setSize(sizeStr);
        setColor(colorStr);
    }

    // --- GETTERS & SETTERS ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    // 3. NYT: HJÆLPE-SETTER TIL SIZE (String -> Enum)
    public void setSize(String sizeStr) {
        if (sizeStr != null) {
            try {
                // Konverterer "xl", "XL", "Xl" -> Size.XL
                this.size = Size.valueOf(sizeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Hvis der skrives noget helt sort (f.eks. "KæmpeStor"), kaster vi en fejl
                throw new IllegalArgumentException("Ugyldig størrelse: " + sizeStr);
            }
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // 4. NYT: HJÆLPE-SETTER TIL COLOR (String -> Enum)
    public void setColor(String colorStr) {
        if (colorStr != null) {
            try {
                // Konverterer "blue", "Blue", "BLUE" -> Color.BLUE
                this.color = Color.valueOf(colorStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Ugyldig farve: " + colorStr);
            }
        }
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
