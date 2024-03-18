module liliths.legacy {
    requires java.rmi;
    requires java.desktop;
    requires java.scripting;
    requires java.logging;
    requires java.xml;
    requires java.datatransfer;
    requires jdk.dynalink;
    requires jdk.jfr;
    requires jdk.zipfs;
    requires jdk.unsupported;
    requires java.net.http;
    requires jdk.jsobject;
    requires jdk.xml.dom;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.web;
    requires org.openjdk.nashorn;
    requires kotlin.stdlib;

    exports com.lilithslegacy;
    exports com.lilithslegacy.main to javafx.graphics;
    exports com.lilithslegacy.controller to javafx.fxml;

    opens com.lilithslegacy.controller to javafx.fxml;

}
