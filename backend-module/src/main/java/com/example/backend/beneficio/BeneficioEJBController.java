package com.example.backend.beneficio;

import com.example.ejb.BeneficioRemote;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;


@Configuration
public class BeneficioEJBController {

    private static String DEFAULT_EJB_URL = "ejb:/ejb-module/BeneficioEjbService!com.example.ejb.BeneficioRemote";
    @Bean
    @Lazy
    public BeneficioRemote getBeneficioRemote() throws NamingException {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        p.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

// Força o uso do protocolo de EJB sobre HTTP
        p.put("jboss.naming.client.ejb.context", "true");

// Usuário e senha (caso não queira depender só do wildfly-config.xml agora)
        p.put(Context.SECURITY_PRINCIPAL, "tulio");
        p.put(Context.SECURITY_CREDENTIALS, "admin123");

        InitialContext context = new InitialContext(p);

// Importante: Para chamadas REMOTAS de fora do Wildfly,
// o nome JNDI não deve começar com "java:".
        // Tente com o .jar no meio, pois o log indicou que o subdeployment tem esse nome
        // Tente sem o .jar no nome do módulo
        return (BeneficioRemote) context.lookup("ear-module-1.0.0-SNAPSHOT/ejb-module/BeneficioEjbService!com.example.ejb.BeneficioRemote");
    }
}
