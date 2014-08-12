package org.jetbrains.kotlin.actions;

import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by user on 11.08.14.
 */
/*
public class TextBoxes extends AnAction {

    // If you register the action from Java code, this constructor is used to set the menu item name
    // (optionally, you can specify the menu description and an icon to display next to the menu item).
    // You can omit this constructor when registering the action in the plugin.xml file.
    public TextBoxes() {
        // Set the menu item name.
        super("Text _Boxes");
        // Set the menu item name, description and icon.
        // super("Text _Boxes","Item description",IconLoader.getIcon("/Mypackage/icon.png"));
    }
@Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        TextFieldWithBrowseButton chooseFiles = new TextFieldWithBrowseButton();
        chooseFiles.show();
        String txt= Messages.showInputDialog(project, "What is your name?", "Input your name", Messages.getQuestionIcon());
        Messages.showMessageDialog(project, "Hello, " + txt + "!\n I am glad to see you.", "Information", Messages.getInformationIcon());

    }
    /*<group id="MyPlugin.SampleMenu" text="Kotlin Playground" description="Sample menu">
    <add-to-group group-id="MainMenu" anchor="last"  />
    <action id="Myplugin" class="TextBoxes" text="Text _Boxes" description="A test menu item" />
    </group>*/

/*
}*/
import com.intellij.CommonBundle;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.filetype.KotlinFileType;


public final class KotlinPlay extends CreateElementActionBase {

    private static final String WHAT = "Kotlin Playground";

    public KotlinPlay() {
        super(WHAT, "Creates new " + WHAT, KotlinFileType.INSTANCE.getIcon());
    }

    @NotNull
    protected PsiElement[] invokeDialog(Project project, PsiDirectory directory) {
        MyInputValidator validator = new MyInputValidator(project, directory);
        Messages.showInputDialog(project, "Enter name for new " + WHAT, "New " + WHAT, Messages.getQuestionIcon(), "", validator);
        return validator.getCreatedElements();
    }

    @NotNull
    protected PsiElement[] create(String newName, PsiDirectory directory) throws Exception {
        KotlinFileType type = KotlinFileType.INSTANCE;
        String ext = type.getDefaultExtension();
        Project project = directory.getProject();
        PsiDirectory moduleDir = directory;
        int length = newName.length() - 1;
        while (newName.charAt(length) == '.') {
            newName = newName.substring(0, length);
            length--;
        }
        String suffix = "." + ext;
        if (newName.toLowerCase().endsWith(suffix)) {
            newName = newName.substring(0, newName.length() - suffix.length());
        }
        String[] fileNames = newName.split("\\.");
        int depth = fileNames.length;
        String moduleName = fileNames[depth - 1];
        boolean needsModuleName = Character.isUpperCase(moduleName.charAt(0));
        String parentPackages = ProjectRootManager.getInstance(project).getFileIndex().getPackageNameByDirectory(directory.getVirtualFile());
        StringBuilder packages = new StringBuilder(
                "".equals(parentPackages) || !needsModuleName
                        ? ""
                        : parentPackages + "."
        );
        for (int i = 0; i < depth - 1; i++) {
            String fileName = fileNames[i];
            if ("".equals(fileName)) {
                throw new IncorrectOperationException("File name cannot be empty");
            }
            char oldChar = fileName.charAt(0);
            String dirName = fileName.replace(oldChar, Character.toUpperCase(oldChar));
            PsiDirectory subDir = moduleDir.findSubdirectory(dirName);
            moduleDir = subDir == null ? moduleDir.createSubdirectory(dirName) : subDir;
            if (needsModuleName) {
                packages.append(dirName).append('.');
            }
        }
        PsiFile file = PsiFileFactory.getInstance(project).createFileFromText(moduleName + "." + ext, type,
                needsModuleName ? "module " + packages + moduleName + " where\n\n" : ""        );
        file = (PsiFile) moduleDir.add(file);
        VirtualFile virtualFile = file.getVirtualFile();
        if (virtualFile != null) {
            FileEditorManager.getInstance(directory.getProject()).openFile(virtualFile, true);
        }
        return new PsiElement[] {file};
    }

    protected String getErrorTitle() {
        return CommonBundle.getErrorTitle();
    }

    protected String getCommandName() {
        return "Create " + WHAT;
    }

    protected String getActionName(PsiDirectory directory, String newName) {
        return WHAT;
    }

    @Override
    protected boolean isAvailable(DataContext dataContext) {
        if (!super.isAvailable(dataContext))
            return false;

        Project project = PlatformDataKeys.PROJECT.getData(dataContext);
        if (project == null) {
            return false;
        }

        IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        if (view == null) {
            return false;
        }

        return true;
    }


}