package com.sencha.gxt.explorer.client.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.thumbs.ExampleThumbs;

/**
 * Model object to represent a GXT example - contains the example widget itself,
 * as well as a name, icon, and some display details about the example.
 * 
 */
public abstract class Example extends NamedModel {

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public static @interface Detail {
    /**
     * Category this example belongs in
     */
    String category();

    /**
     * Other classes, besides the type annotated with {@literal @}Detail to have
     * their source included
     */
    Class<?>[] classes() default {};

    /**
     * Other files useful for demonstrating the example
     */
    String[] files() default {};

    /**
     * Name of the icon method to use in the clientbundle file. See
     * {@link #iconClientBundle()} for the class this method will be called on
     */
    String icon();

    /**
     * ClientBundle type to use to create the icon for this example
     */
    Class<?> iconClientBundle() default ExampleThumbs.class;

    /**
     * The maximum height allowed by this example.
     * <ul>
     * <li>-1 means no maximum height (default)</li>
     * <li>0.0 to 1.0 defines the maximum height in percentage (i.e. 0% to 100%)</li>
     * <li>&gt; 1 defines the maximum height as pixels</li>
     * </ul>
     */
    double maxHeight() default ExampleContainer.DEFAULT_MAX_HEIGHT;

    /**
     * The maximum width allowed by this example.
     * <ul>
     * <li>-1 means no maximum width (default)</li>
     * <li>0.0 to 1.0 defines the maximum width in percentage (i.e. 0% to 100%)</li>
     * <li>&gt; 1 defines the maximum width as pixels</li>
     * </ul>
     */
    double maxWidth() default ExampleContainer.DEFAULT_MAX_WIDTH;

    /**
     * The minimum height allowed by this example.
     * <ul>
     * <li>-1 means no minimum height (default)</li>
     * <li>0.0 to 1.0 defines the minimum height in percentage (i.e. 0% to 100%)</li>
     * <li>&gt; 1 defines the minimum height as pixels</li>
     * </ul>
     */
    double minHeight() default ExampleContainer.DEFAULT_MIN_HEIGHT;

    /**
     * The minimum width allowed by this example.
     * <ul>
     * <li>-1 means no minimum width (default)</li>
     * <li>0.0 to 1.0 defines the minimum width in percentage (i.e. 0% to 100%)</li>
     * <li>&gt; 1 defines the minimum width as pixels</li>
     * </ul>
     */
    double minWidth() default ExampleContainer.DEFAULT_MIN_WIDTH;

    /**
     * Visible name of the example
     */
    String name();

    /**
     * The overflowX for this example.
     */
    Overflow overflowX() default Overflow.AUTO;

    /**
     * The overflowY for this example.
     */
    Overflow overflowY() default Overflow.AUTO;


    /**
     * The preferred height for this example.
     * <ul>
     * <li>-1 means no preferred height (will use native height)</li>
     * <li>0.0 to 1.0 defines the preferred height in percentage (i.e. 0% to 100%, default 50%)</li>
     * <li>&gt; 1 defines the preferred height as pixels</li>
     * </ul>
     */
    double preferredHeight() default ExampleContainer.DEFAULT_PREFERRED_HEIGHT;

    /**
     * The preferred margin for this example.
     * <ul>
     * <li>-1 means no preferred margin (will use 0)</li>
     * <li>0.0 to 1.0 defines the preferred margin in percentage (i.e. 0% to 100%)</li>
     * <li>&gt; 1 defines the preferred margin as pixels (default 20)</li>
     * </ul>
     */
    double preferredMargin() default ExampleContainer.DEFAULT_PREFERRED_MARGIN;

    /**
     * The preferred width for this example.
     * <ul>
     * <li>-1 means no preferred width (will use native width)</li>
     * <li>0.0 to 1.0 defines the preferred width in percentage (i.e. 0% to 100%, default 50%)</li>
     * <li>&gt; 1 defines the preferred width as pixels</li>
     * </ul>
     */
    double preferredWidth() default ExampleContainer.DEFAULT_PREFERRED_WIDTH;
  }

  private IsWidget example;
  private ImageResource icon;
  private double maxHeight;
  private double maxWidth;
  private double minHeight;
  private double minWidth;
  private Overflow overflowX;
  private Overflow overflowY;
  private double preferredHeight;
  private double preferredMargin;
  private double preferredWidth;
  private List<Source> sources = new ArrayList<Source>();

  public Example(String name, ImageResource icon,
                 double minWidth, double minHeight,
                 double maxWidth, double maxHeight,
                 Overflow overflowX, Overflow overflowY,
                 double preferredWidth, double preferredHeight, double preferredMargin) {
    super(name);
    this.icon = icon;

    this.minWidth = minWidth;
    this.minHeight = minHeight;

    this.maxWidth = maxWidth;
    this.maxHeight = maxHeight;

    this.overflowX = overflowX;
    this.overflowY = overflowY;

    this.preferredWidth = preferredWidth;
    this.preferredHeight = preferredHeight;
    this.preferredMargin = preferredMargin;
  }

  public IsWidget getExample() {
    if (example == null) {
      example = createExample();
    }
    return example;
  }

  public ImageResource getIcon() {
    return icon;
  }

  public SafeHtml getImage() {
    SafeHtmlBuilder builder = new SafeHtmlBuilder();
    builder.appendHtmlConstant(AbstractImagePrototype.create(icon).getHTML());
    return builder.toSafeHtml();
  }

  public double getMaxHeight() {
    return maxHeight;
  }

  public double getMaxWidth() {
    return maxWidth;
  }

  public double getMinHeight() {
    return minHeight;
  }

  public double getMinWidth() {
    return minWidth;
  }

  public Overflow getOverflowX() {
    return overflowX;
  }

  public Overflow getOverflowY() {
    return overflowY;
  }

  public double getPreferredHeight() {
    return preferredHeight;
  }

  public double getPreferredMargin() {
    return preferredMargin;
  }

  public double getPreferredWidth() {
    return preferredWidth;
  }

  public List<Source> getSources() {
    return sources;
  }

  public void setIcon(ImageResource icon) {
    this.icon = icon;
  }

  public void setMaxHeight(double maxHeight) {
    this.maxHeight = maxHeight;
  }

  public void setMaxWidth(double maxWidth) {
    this.maxWidth = maxWidth;
  }

  public void setMinHeight(double minHeight) {
    this.minHeight = minHeight;
  }

  public void setMinWidth(double minWidth) {
    this.minWidth = minWidth;
  }

  public void setOverflowX(Overflow overflowX) {
    this.overflowX = overflowX;
  }

  public void setOverflowY(Overflow overflowY) {
    this.overflowY = overflowY;
  }

  public void setPreferredHeight(double preferredHeight) {
    this.preferredHeight = preferredHeight;
  }

  public void setPreferredMargin(double preferredMargin) {
    this.preferredMargin = preferredMargin;
  }

  public void setPreferredWidth(double preferredWidth) {
    this.preferredWidth = preferredWidth;
  }

  protected abstract IsWidget createExample();

}
