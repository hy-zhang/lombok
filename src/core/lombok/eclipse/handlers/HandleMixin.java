package lombok.eclipse.handlers;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.internal.compiler.ast.*;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.lookup.*;
import org.mangosdk.spi.ProviderFor;

import lombok.Mixin;
import lombok.core.AnnotationValues;
import lombok.eclipse.Eclipse;
import lombok.eclipse.EclipseAnnotationHandler;
import lombok.eclipse.EclipseNode;
import static lombok.eclipse.Eclipse.ECLIPSE_DO_NOT_TOUCH_FLAG;
import static lombok.eclipse.handlers.EclipseHandlerUtil.*;

@ProviderFor(EclipseAnnotationHandler.class)
public class HandleMixin extends EclipseAnnotationHandler<Mixin> {
	
	int pS, pE;
	long p;
	
	@Override public void handle(AnnotationValues<Mixin> annotation, Annotation ast, EclipseNode annotationNode) {
		pS = ast.sourceStart;
		pE = ast.sourceEnd;
		p = (long)pS << 32 | pE;
		EclipseNode typeNode = annotationNode.up();
		createOfMethod(typeNode, annotationNode, annotationNode.get(), ast);
	}
	private void createOfMethod(EclipseNode typeNode, EclipseNode error, ASTNode ast, Annotation src) {
		TypeDeclaration typeDecl = (TypeDeclaration) typeNode.get();
		MethodDeclaration of = new MethodDeclaration(typeDecl.compilationResult);
		of.annotations = null;
		of.modifiers = ClassFileConstants.AccStatic;
		of.typeParameters = null;
		of.returnType = new SingleTypeReference("Point".toCharArray(), p);
		of.selector = "of".toCharArray();
		of.arguments = genOfArgs(p);
		of.binding = null;
		of.thrownExceptions = null;
		of.bits |= ECLIPSE_DO_NOT_TOUCH_FLAG;
		of.bodyStart = of.declarationSourceStart = of.sourceStart = ast.sourceStart;
		of.bodyEnd = of.declarationSourceEnd = of.sourceEnd = ast.sourceEnd;
		TypeDeclaration anonymous = genAnonymous(typeDecl, src, ast);
		QualifiedAllocationExpression alloc = new QualifiedAllocationExpression(anonymous);
		alloc.type = copyType(of.returnType);
		ReturnStatement returnStmt = new ReturnStatement(alloc, pS, pE);
		of.statements = new Statement[] { returnStmt };
		injectMethod(typeNode, of);
	}
	private Argument[] genOfArgs(long p) {
		TypeReference intType = new SingleTypeReference(TypeBinding.INT.simpleName, p);
		Argument x = new Argument("x".toCharArray(), p, intType, Modifier.NONE);
		Argument y = new Argument("y".toCharArray(), p, intType, Modifier.NONE);
		return new Argument[]{ x, y };
	}
	private TypeDeclaration genAnonymous(TypeDeclaration typeDecl, Annotation src, ASTNode ast) {
		TypeDeclaration anonymous = new TypeDeclaration(typeDecl.compilationResult);
		anonymous.bits |= (ASTNode.IsAnonymousType | ASTNode.IsLocalType);
		anonymous.name = CharOperation.NO_CHAR;
		anonymous.typeParameters = null;
		anonymous.fields = genFields();
		anonymous.methods = genMethods(typeDecl, ast);
		anonymous.superInterfaces = new TypeReference[]{new SingleTypeReference("Point".toCharArray(), p)};
		return anonymous;
	}
	private FieldDeclaration[] genFields() {
		FieldDeclaration _x = new FieldDeclaration("_x".toCharArray(), pS, pE);
		_x.bits |= Eclipse.ECLIPSE_DO_NOT_TOUCH_FLAG;
		_x.modifiers = ClassFileConstants.AccDefault;
		_x.type = new SingleTypeReference(TypeBinding.INT.simpleName, p);
		_x.initialization = new SingleNameReference("x".toCharArray(), p);
		FieldDeclaration _y = new FieldDeclaration("_y".toCharArray(), pS, pE);
		_y.bits |= Eclipse.ECLIPSE_DO_NOT_TOUCH_FLAG;
		_y.modifiers = ClassFileConstants.AccDefault;
		_y.type = new SingleTypeReference(TypeBinding.INT.simpleName, p);
		_y.initialization = new SingleNameReference("y".toCharArray(), p);
		return new FieldDeclaration[]{ _x, _y };
	}
	private MethodDeclaration[] genMethods(TypeDeclaration typeDecl, ASTNode ast) {
		MethodDeclaration intX = new MethodDeclaration(typeDecl.compilationResult);
		intX.annotations = null;
		intX.modifiers = ClassFileConstants.AccPublic;
		intX.typeParameters = null;
		intX.returnType = new SingleTypeReference(TypeBinding.INT.simpleName, p);
		intX.selector = "x".toCharArray();
		intX.arguments = null;
		intX.binding = null;
		intX.thrownExceptions = null;
		intX.bits |= ECLIPSE_DO_NOT_TOUCH_FLAG;
		intX.bodyStart = intX.declarationSourceStart = intX.sourceStart = ast.sourceStart;
		intX.bodyEnd = intX.declarationSourceEnd = intX.sourceEnd = ast.sourceEnd;
		ReturnStatement return_intX = new ReturnStatement(new SingleNameReference("_x".toCharArray(), p), pS, pE);
		intX.statements = new Statement[] { return_intX };
		MethodDeclaration intY = new MethodDeclaration(typeDecl.compilationResult);
		intY.annotations = null;
		intY.modifiers = ClassFileConstants.AccPublic;
		intY.typeParameters = null;
		intY.returnType = new SingleTypeReference(TypeBinding.INT.simpleName, p);
		intY.selector = "y".toCharArray();
		intY.arguments = null;
		intY.binding = null;
		intY.thrownExceptions = null;
		intY.bits |= ECLIPSE_DO_NOT_TOUCH_FLAG;
		intY.bodyStart = intY.declarationSourceStart = intY.sourceStart = ast.sourceStart;
		intY.bodyEnd = intY.declarationSourceEnd = intY.sourceEnd = ast.sourceEnd;
		ReturnStatement return_intY = new ReturnStatement(new SingleNameReference("_y".toCharArray(), p), pS, pE);
		intY.statements = new Statement[] { return_intY };
		MethodDeclaration voidX = new MethodDeclaration(typeDecl.compilationResult);
		voidX.annotations = null;
		voidX.modifiers = ClassFileConstants.AccPublic;
		voidX.typeParameters = null;
		voidX.returnType = new SingleTypeReference(TypeBinding.VOID.simpleName, p);
		voidX.selector = "x".toCharArray();
		Argument paramX = new Argument("x".toCharArray(), p, new SingleTypeReference(TypeBinding.INT.simpleName, p), Modifier.NONE);
		paramX.sourceStart = pS; paramX.sourceEnd = pE;
		voidX.arguments = new Argument[]{ paramX };
		voidX.binding = null;
		voidX.thrownExceptions = null;
		voidX.bits |= ECLIPSE_DO_NOT_TOUCH_FLAG;
		voidX.bodyStart = voidX.declarationSourceStart = voidX.sourceStart = ast.sourceStart;
		voidX.bodyEnd = voidX.declarationSourceEnd = voidX.sourceEnd = ast.sourceEnd;
		Assignment assign_voidX = new Assignment(new SingleNameReference("_x".toCharArray(), p), new SingleNameReference("x".toCharArray(), p), (int)p);
		assign_voidX.sourceStart = pS; assign_voidX.sourceEnd = assign_voidX.statementEnd = pE;
		voidX.statements = new Statement[] { assign_voidX };
		MethodDeclaration voidY = new MethodDeclaration(typeDecl.compilationResult);
		voidY.annotations = null;
		voidY.modifiers = ClassFileConstants.AccPublic;
		voidY.typeParameters = null;
		voidY.returnType = new SingleTypeReference(TypeBinding.VOID.simpleName, p);
		voidY.selector = "y".toCharArray();
		Argument paramY = new Argument("y".toCharArray(), p, new SingleTypeReference(TypeBinding.INT.simpleName, p), Modifier.NONE);
		paramY.sourceStart = pS; paramY.sourceEnd = pE;
		voidY.arguments = new Argument[]{ paramY };
		voidY.binding = null;
		voidY.thrownExceptions = null;
		voidY.bits |= ECLIPSE_DO_NOT_TOUCH_FLAG;
		voidY.bodyStart = voidY.declarationSourceStart = voidY.sourceStart = ast.sourceStart;
		voidY.bodyEnd = voidY.declarationSourceEnd = voidY.sourceEnd = ast.sourceEnd;
		Assignment assign_voidY = new Assignment(new SingleNameReference("_y".toCharArray(), p), new SingleNameReference("y".toCharArray(), p), (int)p);
		assign_voidY.sourceStart = pS; assign_voidY.sourceEnd = assign_voidY.statementEnd = pE;
		voidY.statements = new Statement[] { assign_voidY };
		MethodDeclaration withX = new MethodDeclaration(typeDecl.compilationResult);
		withX.annotations = null;
		withX.modifiers = ClassFileConstants.AccPublic;
		withX.typeParameters = null;
		withX.returnType = new SingleTypeReference("Point".toCharArray(), p);
		withX.selector = "withX".toCharArray();
		Argument param_withX = new Argument("x".toCharArray(), p, new SingleTypeReference(TypeBinding.INT.simpleName, p), Modifier.NONE);
		param_withX.sourceStart = pS; param_withX.sourceEnd = pE;
		withX.arguments = new Argument[]{ param_withX };
		withX.binding = null;
		withX.thrownExceptions = null;
		withX.bits |= ECLIPSE_DO_NOT_TOUCH_FLAG;
		withX.bodyStart = withX.declarationSourceStart = withX.sourceStart = ast.sourceStart;
		withX.bodyEnd = withX.declarationSourceEnd = withX.sourceEnd = ast.sourceEnd;
		MessageSend callY = new MessageSend();
		callY.receiver = new ThisReference(pS, pE);
		callY.selector = "y".toCharArray();
		MessageSend callOf = new MessageSend();
		callOf.receiver = new SingleNameReference("Point".toCharArray(), p);
		callOf.selector = "of".toCharArray();
		callOf.arguments = new Expression[]{ new SingleNameReference("x".toCharArray(), p), callY };
		ReturnStatement return_withX = new ReturnStatement(callOf, pS, pE);
		withX.statements = new Statement[] { return_withX };
		return new MethodDeclaration[]{ intX, intY, voidX, voidY, withX };
	}
}
