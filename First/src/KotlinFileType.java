import javax.swing.Icon;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KotlinFileType extends LanguageFileType {

    private  Icon myIcon = KotlinIcons.DEFAULT;
    public static KotlinFileType INSTANCE  =  new KotlinFileType();
    public String DEFAULT_EXTENSION = "kt.playground";
    public KotlinFileType(){
        super(KotlinLanguage.INSTANCE);

    }

    @NotNull
    @Override
    public String getName() {
        return "KotlinPlayground file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "KotlinPlayground file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return myIcon;
    }





}/**
 * Created by user on 11.08.14.
 */
