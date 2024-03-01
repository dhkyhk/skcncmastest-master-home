package skcnc.framework.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

public class YamlPropertySourceFactory implements PropertySourceFactory {
	@Override
	public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource encodedResource) throws IOException {
		Properties properties = null;
		try {
			YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
			factory.setResources(encodedResource.getResource());
			properties = factory.getObject();
		}catch(IllegalStateException ex) {
			Throwable cause = ex.getCause();
			if(cause instanceof FileNotFoundException) {
				throw (FileNotFoundException) cause;
			}
			throw ex;
		}
		
		String sourcename = name != null ? name : encodedResource.getResource().getFilename();
		return new PropertiesPropertySource(sourcename, properties);
	}
}
