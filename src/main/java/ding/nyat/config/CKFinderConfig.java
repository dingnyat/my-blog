package ding.nyat.config;

import com.ckfinder.connector.configuration.AccessControlLevelsList;
import com.ckfinder.connector.configuration.Configuration;
import com.ckfinder.connector.configuration.ConfigurationPathBuilder;
import com.ckfinder.connector.configuration.Events;
import com.ckfinder.connector.data.AccessControlLevel;
import com.ckfinder.connector.data.PluginInfo;
import com.ckfinder.connector.data.PluginParam;
import com.ckfinder.connector.data.ResourceType;
import com.ckfinder.connector.errors.ConnectorException;
import com.ckfinder.connector.plugins.ImageResize;
import com.ckfinder.connector.plugins.Watermark;
import com.ckfinder.connector.utils.PathUtils;
import ding.nyat.util.PathConstants;

import javax.servlet.ServletConfig;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class CKFinderConfig extends Configuration {

    // Chú ý: vendor là đường dẫn static đến folder ckfinder trong static
    // ckfinder.js cần jquery nhá
    public static final String CKFINDER_URL_PATTERN = "/vendor" + "/ckfinder/core/connector/java/connector.java";

    public CKFinderConfig(ServletConfig servletConfig) {
        super(servletConfig);
    }

    @Override
    public void init() throws Exception {
        Class<?> clazz = getClass().getSuperclass();

        Field field = clazz.getDeclaredField("lastCfgModificationDate");
        field.setAccessible(true);
        field.set(this, System.currentTimeMillis());

        Method method = clazz.getDeclaredMethod("clearConfiguration");
        method.setAccessible(true);
        method.invoke(this);

        this.xmlFilePath = null;

        this.enabled = true;

        this.baseDir = PathUtils.escape(PathUtils.addSlashToEnd(PathConstants.UPLOAD_BASE_DIR));
        this.baseURL = PathUtils.escape(PathUtils.addSlashToEnd(this.servletConf.getInitParameter("baseUrl")));

        this.licenseName = "dingnyat";
        this.licenseKey = "RSFAG1EQ2XKHSFY335S7UKDRUQLN6KKT";

        this.imgWidth = 700;
        this.imgHeight = 700;
        this.imgQuality = 1F;
        this.thumbsEnabled = true;
        this.thumbsURL = this.baseURL + "_thumbs/";
        this.thumbsDir = this.baseDir + "_thumbs";
        this.thumbsPath = "";
        this.thumbsQuality = 0.5F;
        this.thumbsDirectAccess = false;
        this.thumbsMaxHeight = 50;
        this.thumbsMaxWidth = 50;

        this.userRoleSessionVar = "CKFINDER_ROLE";
        this.accessControlLevels = new AccessControlLevelsList<>(true);
        AccessControlLevel acl = new AccessControlLevel();
        acl.setRole("*");
        acl.setResourceType("*");
        acl.setFolder("/");
        acl.setFileDelete(true);
        acl.setFileRename(true);
        acl.setFileUpload(true);
        acl.setFileView(true);
        acl.setFolderDelete(true);
        acl.setFolderRename(true);
        acl.setFolderCreate(true);
        acl.setFolderView(true);
        accessControlLevels.addItem(acl, false);

        this.hiddenFolders = Arrays.asList(".*", "CVS");

        this.hiddenFiles = Collections.singletonList(".*");

        this.htmlExtensions = Arrays.asList("html", "htm", "xml", "js");

        this.basePathBuilder = new ConfigurationPathBuilder();

        this.doubleExtensions = true;
        this.forceASCII = false;
        this.checkSizeAfterScaling = true;
        this.uriEncoding = "UTF-8";
        this.secureImageUploads = true;
        this.disallowUnsafeCharacters = false;
        this.enableCsrfProtection = true;

        this.defaultResourceTypes = new LinkedHashSet<>();

        this.types = new LinkedHashMap<>();
        ResourceType type = new ResourceType("Image");
        type.setUrl(this.baseURL + PathConstants.IMAGE_PREFIX_URL + PathConstants.DOWNLOAD_PREFIX_URL);
        type.setPath(PathConstants.IMAGE_UPLOAD_DIR);
        type.setMaxSize("0");
        type.setDeniedExtensions(null);
        type.setAllowedExtensions("bmp,gif,jpeg,jpg,png");
        this.types.put(type.getName(), type);

        PluginInfo imgResizePlg = new PluginInfo();
        imgResizePlg.setName("imageresize");
        imgResizePlg.setEnabled(true);
        imgResizePlg.setClassName(ImageResize.class.getTypeName());
        PluginParam smThumb = new PluginParam();
        smThumb.setName("smallThumb");
        smThumb.setValue("90x90");
        PluginParam mdThumb = new PluginParam();
        mdThumb.setName("mediumThumb");
        mdThumb.setValue("120x120");
        PluginParam lgThumb = new PluginParam();
        lgThumb.setName("largeThumb");
        lgThumb.setValue("180x180");
        imgResizePlg.setParams(Arrays.asList(smThumb, mdThumb, lgThumb));

        PluginInfo watermark = new PluginInfo();
        watermark.setName("watermark");
        watermark.setEnabled(true);
        watermark.setClassName(Watermark.class.getTypeName());
        PluginParam param1 = new PluginParam();
        param1.setName("source");
        param1.setValue("/path/in/servlet/context/logo.gif");
        PluginParam param2 = new PluginParam();
        param2.setName("transparency");
        param2.setValue("0.8");
        PluginParam param3 = new PluginParam();
        param3.setName("quality");
        param3.setValue("100");
        PluginParam param4 = new PluginParam();
        param4.setName("marginRight");
        param4.setValue("5");
        PluginParam param5 = new PluginParam();
        param5.setName("marginBottom");
        param5.setValue("5");
        watermark.setParams(Arrays.asList(param1, param2, param3, param4, param5));
        watermark.setInternal(true);

        this.plugins = Arrays.asList(imgResizePlg, watermark);

        field = clazz.getDeclaredField("events");
        field.setAccessible(true);
        field.set(this, new Events());
        this.registerEventHandlers();
    }

    @Override
    public boolean checkIfReloadConfig() throws ConnectorException {
        return false; // tắt cái logger error vì ko dung xml đi
    }
}
