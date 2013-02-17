// Place your Spring DSL code here
beans = {
/* French setting: */	
	localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
		defaultLocale = new Locale("fr","FR")
		java.util.Locale.setDefault(defaultLocale)
	 }
}
