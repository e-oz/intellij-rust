package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import org.rust.ide.icons.RustIcons
import org.rust.lang.core.psi.RustFieldDeclElement
import org.rust.lang.core.psi.iconWithVisibility
import org.rust.lang.core.psi.impl.RustPsiImplUtil
import org.rust.lang.core.psi.impl.RustStubbedNamedElementImpl
import org.rust.lang.core.stubs.elements.RustFieldDeclElementStub
import javax.swing.Icon

abstract class RustFieldDeclImplMixin : RustStubbedNamedElementImpl<RustFieldDeclElementStub>, RustFieldDeclElement {
    constructor(node: ASTNode) : super(node)

    constructor(stub: RustFieldDeclElementStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    override fun getIcon(flags: Int): Icon =
        iconWithVisibility(flags, RustIcons.FIELD)

    override val isPublic: Boolean get() = RustPsiImplUtil.isPublic(this)

}
