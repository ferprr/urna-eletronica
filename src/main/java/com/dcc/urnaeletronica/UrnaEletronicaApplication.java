package com.dcc.urnaeletronica;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dcc.urnaeletronica.util.Util;

@SpringBootApplication
public class UrnaEletronicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrnaEletronicaApplication.class, args);
		try
		{
			System.out.println("Senha '12345678' criptografada: " + Util.criptografarSenha("12345678"));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}

}
