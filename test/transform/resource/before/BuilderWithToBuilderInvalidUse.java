//skip compare content
import java.util.List;

import lombok.Builder;
import lombok.Builder.ObtainVia;
import lombok.Getter;
class BuilderWithToBuilderInvalidUse<T> {
	
	@Builder(toBuilder = true)
	static class EmptyObtainVia { 
		@ObtainVia
		int foo;
	}
	
	@Builder(toBuilder = true)
	static class FieldAndMethodObtainVia { 
		@ObtainVia(field="foo", method="foo")
		@Getter
		int foo;
	}
	
	@Builder(toBuilder = true)
	static class FieldAndIsStaticObtainVia { 
		@ObtainVia(field="foo", isStatic=true)
		int foo;
	}
	
	@Builder(toBuilder = true)
	static Integer foo(int bar) {
		return bar;
	}
	
	static class Inner<E> {
		@Builder(toBuilder = true)
		static BuilderWithToBuilderInvalidUse<String>.Inner<Integer> foo(Integer element) {
			return null;
		}
	}
	
	@Builder(toBuilder = true)
	static <K, V> BuilderWithToBuilderInvalidUse<K> creator(K key, V value) {
		return null;
	}
	
	static class StaticWithToBuilderMore<T, K> {
		private T foo;
		private K bar;
		@Builder(toBuilder = true)
		public static <Z> StaticWithToBuilderMore<Z, Z> test(@Builder.ObtainVia(field = "bar") Z foo) {
			return new StaticWithToBuilderMore<Z, Z>();
		}
	}
}