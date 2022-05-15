package study.benchmark.annotation;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author wangdengwu
 */
@SupportedAnnotationTypes("study.benchmark.annotation.HelloWorld")
public class ChildrenProcessor extends AbstractProcessor {
    public static final String SUFFIX = "Children";
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Children.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (element.getKind() != ElementKind.CLASS) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Only classes can be annotated with @Children", element);
                }
                String classPath = elementUtils.getPackageOf(element).getQualifiedName().toString() + "." + element.getSimpleName().toString() + SUFFIX;
                MethodSpec helloWorldMethod = MethodSpec.methodBuilder("helloWorld")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addStatement("$T.out.println($S)", System.class, "Hello, World!")
                        .build();
                TypeSpec children = TypeSpec.classBuilder(element.getSimpleName().toString() + SUFFIX)
                        .addJavadoc(CodeBlock.builder().add("@author wangdengwu").build())
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addMethod(helloWorldMethod)
                        .superclass(element.asType())
                        .build();
                JavaFile javaFile = JavaFile.builder(elementUtils.getPackageOf(element).getQualifiedName().toString(), children).build();
                try {
                    JavaFileObject sourceFile = filer.createSourceFile(classPath, element);
                    Writer writer = sourceFile.openWriter();
                    writer.write(javaFile.toString());
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
