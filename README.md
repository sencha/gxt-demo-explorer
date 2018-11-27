# GXT Examples Explorer

<img src='./theapp.png' width='400px' />

## Demo

* [Example Explorer Demo](http://examples.sencha.com/gxt/latest)

## GXT 

* [Sencha GXT Product Info](https://www.sencha.com/products/gxt/)
* [GXT Guides](http://docs.sencha.com/gxt/4.x/)


## Import
Start by importing the project as an existing maven project. 

### Add GXT Dependency
Add this xml element to `~/.m2/settings.xml` to get access to the GXT 4.0.3 artifacts. 

* More in the [GXT Maven Guide](https://docs.sencha.com/gxt/4.x/guides/getting_started/maven/Maven.html)

```
<!-- Add this to ~/.m2/settings.xml servers -->
<server>
    <id>sencha-gxt-repository</id>
    <username>[portal username]</username>
    <password>[portal password]</password>
</server>
```

### Adding Ext JS Integration
Easily add Ext JS integration with the GWT JSNI or JsInterop Apis.

Integration instructions:
1. Download Ext JS zip. (In the near future it will be installed using Maven)
2. Import the resources into the [index.html](https://github.com/sencha/gxt-demo-explorer/blob/master/src/main/webapp/index.html). Find the Ext JS comments.
3. Add the resources to the [/extjs](https://github.com/sencha/gxt-demo-explorer/tree/master/src/main/webapp/extjs) directory. 


## Debugging

### Debugging in Eclipse

1. Right click on the project and Debug As `GWT Development Mode with Jetty`
2. Double click on the url in the `Development Mode` web url.  
