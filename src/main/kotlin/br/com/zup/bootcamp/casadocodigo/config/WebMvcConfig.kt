package br.com.zup.bootcamp.casadocodigo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*

/**
 * https://www.baeldung.com/web-mvc-configurer-adapter-deprecated
 */
@Configuration
class WebMvcConfig : WebMvcConfigurer {

    companion object {
        private val PT_BR = Locale("pt", "BR")
    }

    @Bean
    fun localeResolver(): LocaleResolver {
        val resolver = CookieLocaleResolver()
        resolver.setDefaultLocale(PT_BR)
        return resolver
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val interceptor = LocaleChangeInterceptor()
        interceptor.paramName = "lang"
        return interceptor
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

}