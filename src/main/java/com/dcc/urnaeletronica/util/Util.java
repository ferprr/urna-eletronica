package com.dcc.urnaeletronica.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util
{
	public static String criptografarSenha(String senha) throws NoSuchAlgorithmException
	{
		MessageDigest msgDig = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, msgDig.digest(senha.getBytes()));
		return hash.toString(16);
	}
	
}
