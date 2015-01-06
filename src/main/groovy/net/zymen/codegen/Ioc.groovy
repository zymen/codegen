package net.zymen.codegen

class Ioc {
    static Ioc obj
    Map<Object, Object> container

    private Ioc() {
        this.container = new HashMap<Object, Object>()
    }

    static Ioc instance() {
        if (this.obj == null) {
            this.obj = new Ioc()
        }

        this.obj
    }

    Ioc register(Object key, Object value) {
        this.container.put(key, value)
        this
    }

    public <T> T get(Class<T> type) {
        (T)this.container.get(type)
    }
}
