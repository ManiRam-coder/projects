package com.log4j2;

import java.io.IOException;
import java.net.URI;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "CustomConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(50)
public class CustomConfigurationFactory extends ConfigurationFactory {

	static String filepath = "D:\\source\\Log4j2\\logs\\";


	static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) throws IOException {

		builder.setStatusLevel( Level.DEBUG);
		builder.setConfigurationName("RollingBuilder");


		// create a rolling file appender start
		LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
				.addAttribute("pattern", "%d [%t] %-5level: %msg%n");
		ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
				.addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "2KB"));

		ComponentBuilder defaultCount = builder.newComponent("DefaultRolloverStrategy").addAttribute("max", "5");

		AppenderComponentBuilder appenderBuilder = builder.newAppender(name, "RollingFile")
				.addAttribute("fileName", filepath+""+name+".log")
				.addAttribute("filePattern", filepath+""+name+"-%i.log")
				.add(layoutBuilder)
				.addComponent(defaultCount)
				.addComponent(triggeringPolicy);

		builder.add( appenderBuilder );
		// create a rolling file appender ends

		// create a rolling file appender2 start
		String name2 = "ExternalServiceFile";
		AppenderComponentBuilder appenderBuilder2 = builder.newAppender(name2, "RollingFile")
				.addAttribute("fileName", filepath+""+name2+".log")
				.addAttribute("filePattern", filepath+""+name2+"-%i.log")
				.add(layoutBuilder)
				.addComponent(defaultCount)
				.addComponent(triggeringPolicy);

		//builder.add( appenderBuilder2 );
		// create a rolling file appender2 ends

		// create the new logger
		builder.add( builder.newLogger( name, Level.DEBUG )
				.add( builder.newAppenderRef( name ).addAttribute("level", Level.DEBUG) ) 
				.addAttribute( "additivity", false ) );

		/*builder.add( builder.newLogger( name2, Level.DEBUG )
				.add( builder.newAppenderRef( name2 ).addAttribute("level", Level.DEBUG) ) 
				.addAttribute( "additivity", false ) );*/

		builder.add( builder.newRootLogger( Level.DEBUG )
				.add( builder.newAppenderRef( "rolling" ).addAttribute("level", Level.DEBUG) ) );
		builder.writeXmlConfiguration(System.out);
		return builder.build();
	}

	@Override
	public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
		return getConfiguration(loggerContext, source.toString(), null);
	}

	@Override
	public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
		ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
		Configuration config = null;
		String fileName = "ExternalServiceFile";
		try { 
			config = createConfiguration(name, builder);

			for(Entry<String, Appender> en : config.getAppenders().entrySet()) {
				System.out.println(en.getKey() +"/"+en.getValue());
			}
			LoggerContext ctx = Configurator.initialize(builder.build());


			config = updateAppender(fileName,config,ctx); 

			/*Layout layout = PatternLayout.createLayout("%d [%t] %-5level: %msg%n", null, config, null,
					null,false, false, null, null);

			SizeBasedTriggeringPolicy policy = SizeBasedTriggeringPolicy.createPolicy("1KB");

			DefaultRolloverStrategy strategy = DefaultRolloverStrategy.
					createStrategy("10", "0", null, null, null, false, config);


			Appender appender = RollingFileAppender.createAppender(filepath + fileName+".log", filepath + fileName+"-%i.log",
					"true", fileName, "false", "128", "true", policy, strategy, layout, (Filter) null, "false", "false", (String) null, config);
			appender.start();
		//	config.addAppender(appender);
			
			ctx.getConfiguration().addAppender(appender);
			
			AppenderRef ref = AppenderRef.createAppenderRef(fileName, Level.DEBUG, null);
			AppenderRef[] refs = new AppenderRef[] {ref};
			LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.DEBUG, fileName,
					"true", refs, null, config, null );
			loggerConfig.addAppender(appender, null, null);
			ctx.getConfiguration().addLogger(fileName, loggerConfig);
			//config.addLogger(fileName, loggerConfig);
			
			for(Entry<String, Appender> en : config.getAppenders().entrySet()) {
				System.out.println("--->"+en.getKey() +"/"+en.getValue());
			}
		//	ctx.getRootLogger().addAppender(ctx.getConfiguration().getAppender(fileName));
			ctx.updateLoggers();*/
			//ctx.reconfigure();

			for(Entry<String, Appender> en : config.getAppenders().entrySet()) {
				System.out.println(en.getKey() +"/"+en.getValue());
			}

			//Logger log= LogManager.getLogger(name);
			Logger log = ctx.getLogger(name);
			log.info("Writing from dynamic config#"+name);
			log.debug("debug Writing from dynamic config#"+name);
			log.warn("warn Writing from dynamic config#"+name);
			log.error("error Writing from dynamic config#"+name);

			//Logger log1= LogManager.getLogger(fileName);
			Logger log1 = ctx.getLogger(fileName);
			log1.info("Writing from dynamic config ---#ExternalServiceFile");
			log1.debug("debug Writing from dynamic config ---#ExternalServiceFile");
			log1.warn("warn Writing from dynamic config ---#ExternalServiceFile");
			log1.error("error Writing from dynamic config ---#ExternalServiceFile");
			//	final LoggerContext context = (LoggerContext) LogManager.getContext();//(false);
			//	final Configuration configLatest = context.getConfiguration();
		} 
		catch (Exception e) {

			// TODO Auto-generated catch block e.printStackTrace(); 
		}
		return config;
	}

	private Configuration updateAppender(String name,Configuration config,LoggerContext ctx) throws IOException {
		//final LoggerContext ctx = (LoggerContext) LogManager.getContext();//(false);
		//	final LoggerContext ctx = (LoggerContext)((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).getContext();
		//final Configuration config = ctx.getConfiguration();
		Layout layout = PatternLayout.createLayout("%d [%t] %-5level: %msg%n", null, config, null,
				null,false, false, null, null);

		SizeBasedTriggeringPolicy policy = SizeBasedTriggeringPolicy.createPolicy("1KB");

		DefaultRolloverStrategy strategy = DefaultRolloverStrategy.
				createStrategy("5", "0", "min", null, null, false, config);


		Appender appender = RollingFileAppender.createAppender(filepath + name+".log", filepath + name+"-%i.log",
				"true", name, "false", "128", "true", policy, strategy, layout, (Filter) null, "false", "false", (String) null, config);
		appender.start();
		//config.addAppender(appender);
		ctx.getConfiguration().addAppender(appender);
		AppenderRef ref = AppenderRef.createAppenderRef(name, Level.DEBUG, null);
		AppenderRef[] refs = new AppenderRef[] {ref};
		LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.DEBUG, name,
				"true", refs, null, config, null );
		loggerConfig.addAppender(appender, null, null);

		//config.addLogger(name, loggerConfig);
		ctx.getConfiguration().addLogger(name, loggerConfig);
		ctx.updateLoggers();
		//ctx.reconfigure();
		return config;
	}
	@Override
	protected String[] getSupportedTypes() {
		return new String[] {"*"};
	}
}