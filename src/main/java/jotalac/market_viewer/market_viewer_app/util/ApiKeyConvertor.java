package jotalac.market_viewer.market_viewer_app.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Converter
@Component
@RequiredArgsConstructor
public class ApiKeyConvertor implements AttributeConverter<String, String> {

    private final TextEncryptor textEncryptor;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return (attribute == null) ? null : textEncryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : textEncryptor.decrypt(dbData);
    }
}
