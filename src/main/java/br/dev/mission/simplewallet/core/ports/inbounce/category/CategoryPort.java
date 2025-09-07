package br.dev.mission.simplewallet.core.ports.inbounce.category;

import java.util.List;

import br.dev.mission.simplewallet.core.model.category.CategoryCore;

public interface CategoryPort {
    void createCategory(CategoryCore category);
    CategoryCore getCategoryById(Long id);
    void updateCategory(CategoryCore category);
    void deleteCategory(Long id);
    List<CategoryCore> getAllCategories();
}
