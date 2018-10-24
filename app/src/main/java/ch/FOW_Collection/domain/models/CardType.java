package ch.FOW_Collection.domain.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CardType extends MultiLanguageString implements Entity, Serializable {
    public static final String COLLECTION = "cardType";

    @Exclude
    private String id;
}
