package net.pixaurora.kit_tunes.impl.config.dispatch;

import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.pixaurora.kit_tunes.impl.config.DualSerializer;

public record DispatchGroup<A extends SpecifiesType<A>, T extends DispatchType<A>>(String typeName, List<T> types) {
	public <G> T lookupBy(String lookupType, Function<T, G> getter, G lookupObject) {
		Optional<T> foundType = this.types.stream()
				.filter(type -> getter.apply(type).equals(lookupObject))
				.findFirst();

		try {
			return foundType.get();
		} catch (NoSuchElementException typeNotFound) {
			throw new JsonParseException(this.typeName + " lookup did not find a type with " + lookupType + "  = `" + lookupObject.toString() + "`");
		}
	}

	public T lookupName(String name) {
		return this.lookupBy("name", DispatchType::name, name);
	}

	public Serializer<A, T> itemSerialzier() {
		return new Serializer<>(this);
	}

	public static final class Serializer<A extends SpecifiesType<A>, T extends DispatchType<A>> implements DualSerializer<A> {
		public static final String TYPE_FIELD = "type";

		private final DispatchGroup<A, T> group;

		public Serializer(DispatchGroup<A, T> group) {
			this.group = group;
		}

		@Override
		public JsonElement serialize(A item, Type _type, JsonSerializationContext context) {
			DispatchType<A> typeOfItem = item.type();

			JsonObject itemData = context.serialize(item, typeOfItem.targetClass()).getAsJsonObject();
			itemData.addProperty(TYPE_FIELD, typeOfItem.name());

			return itemData;
		}

		@Override
		public A deserialize(JsonElement element, Type _type, JsonDeserializationContext context) throws JsonParseException {
			JsonObject itemData = element.getAsJsonObject();

			T typeOfItem = this.group.lookupName(itemData.getAsJsonPrimitive(TYPE_FIELD).getAsString());
			A item = context.deserialize(itemData, typeOfItem.targetClass());

			return item;
		}
	}
}
