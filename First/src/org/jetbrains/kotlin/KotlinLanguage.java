package org.jetbrains.kotlin;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.highlight.KotlinHighLighter;

public class KotlinLanguage extends Language {
    public static final KotlinLanguage INSTANCE = new KotlinLanguage();

    public KotlinLanguage() {
        super("Kotlin", "text/kotlin");
        SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
            @NotNull
            protected SyntaxHighlighter createHighlighter() {
                return new KotlinHighLighter();//here not null!!!!
            }
        });
    }
}