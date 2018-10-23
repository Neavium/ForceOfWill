package ch.FOW_Collection.data.repositories;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import ch.FOW_Collection.domain.models.Card;
import ch.FOW_Collection.domain.models.CardEdition;
import ch.FOW_Collection.domain.utils.FirestoreQueryLiveData;
import ch.FOW_Collection.domain.utils.FirestoreQueryLiveDataArray;

import static androidx.lifecycle.Transformations.map;

public class CardEditionsRepository {
    private final FirestoreQueryLiveDataArray<CardEdition> allCardEditions =
            new FirestoreQueryLiveDataArray<>(
                    FirebaseFirestore
                        .getInstance()
                        .collection(CardEdition.COLLECTION), CardEdition.class);

    private static LiveData<Card> cardEditionById(String cardId) {
        return new FirestoreQueryLiveData<>(
                FirebaseFirestore
                        .getInstance()
                        .collection(Card.COLLECTION)
                        .document(cardId), Card.class);
    }

    public LiveData<List<CardEdition>> getAllEditions() {
        return allCardEditions;
    }

}
