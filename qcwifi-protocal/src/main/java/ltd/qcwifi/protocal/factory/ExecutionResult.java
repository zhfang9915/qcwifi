package ltd.qcwifi.protocal.factory;

public class ExecutionResult {
	boolean handled;
	Object result;
	public boolean isHandled() {
		return handled;
	}
	public void setHandled(boolean handled) {
		this.handled = handled;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
