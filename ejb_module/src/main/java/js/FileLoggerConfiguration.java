//package js;
//
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.core.Appender;
//import org.apache.logging.log4j.core.Layout;
//import org.apache.logging.log4j.core.Filter.Result;
//import org.apache.logging.log4j.core.appender.RollingFileAppender;
//import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
//import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
//import org.apache.logging.log4j.core.config.BaseConfiguration;
//import org.apache.logging.log4j.core.filter.ThresholdFilter;
//import org.apache.logging.log4j.core.layout.PatternLayout;
//
//public class FileLoggerConfiguration extends BaseConfiguration {
//
//	/**
//	 * The name of the default configuration
//	 * */
//	public static final String DEFAULT_NAME = "RollingFileLogger";
//
//	/**
//	 * Creates file logger configuration
//	 */
//	public FileLoggerConfiguration() {
//		// TODO: check logging
//		setName(DEFAULT_NAME);
//		final Layout<?> layout = PatternLayout.createLayout(
//				"%d{dd-MM-yyyy HH:mm:ss.SSS} "
//						+ PatternLayout.TTCC_CONVERSION_PATTERN, null, null,
//				"UTF-8");
//		ThresholdFilter filter = ThresholdFilter.createFilter("ERROR",
//				Result.ACCEPT.toString(), Result.DENY.toString());
//		final Appender<?> appender = RollingFileAppender.createAppender(
//				"logs/error.log", "error.%i.log", "true",
//				"HibernateUtilFileAppender", "false", "true",
//				SizeBasedTriggeringPolicy.createPolicy("1MB"),
//				DefaultRolloverStrategy.createStrategy("4", "1", "4", this),
//				layout, filter, "true", null);
//		appender.start();
//		addAppender(appender);
//		getRootLogger().addAppender(appender, Level.ERROR, filter);
//		//Configurator.initialize(ClassLoader.getSystemClassLoader(), "");
//	}
//
//	@Override
//	protected void doConfigure() {
//	}
// }