package com.wang;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityApp 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(SecurityApp.class, args);
    }
    
    @Bean("commandLineRunner")
    public CommandLineRunner commandLineRunner() {
    	return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				System.err.print("\n********\n---springapplication 启动时后，会调用这个回调接口的，可以翻看源码---\n************\n");
			}
		};
    }
}
