package net.zymen.codegen.template

class FileTemplate implements Template{
    String filename

    public FileTemplate(String filename) {
        this.filename = filename
    }

    @Override
    String getContent() {
        new File(this.filename).text
    }
}
