package net.zymen.codegen.template

class StringTemplate implements Template{
    String content

    public StringTemplate(String content) {
        this.content = content
    }

    @Override
    String getContent() {
        this.content
    }
}
