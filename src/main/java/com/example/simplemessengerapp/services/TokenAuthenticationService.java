package com.example.simplemessengerapp.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

//  Сервис для работы с токенами
@Service
public class TokenAuthenticationService {

    //  Константы для генерации токена
    private static final Long TOKEN_EXPIRY = 864000000L;
    private static final String SECRET = "de23gt50opq23gy2";
    private static final String TOKEN_PREFIX = "name";

    //  Ассоциативный массив для хранения выданных в текщей сессии токенов
    private HashMap<String, String> tokenMap = new HashMap<>();

    //  Генерация токена по имени
    public String generateToken(String name) {
        if (tokenMap.containsKey(name)) //  Если в мапе уже есть есть имя
            return tokenMap.get(name);  //  Возвращаем уже сгенерированный токен из мапы
        String token = Jwts.builder()   //  генерируем новый токен
                .setSubject(name)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRY))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        tokenMap.put(name, token);      //  Записываем пару имя-токен в мапу
        return token;
    }

    //  Проверяем токен на наличие имени (name - проверяемое имя, token - проверяемый токен)
    public boolean checkToken(String name, String token) {
        if (!tokenMap.containsValue(token))
            return false;
        return name.equals(extractFromToken(token));
    }

    //  Извлекаем имя из токена
    public String extractFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody().getSubject();
    }
}
