import {Navigate,useLocation} from 'react-router-dom';
import {useAuth} from '../context/AuthContext';
export default function ProtectedRoute({children}){const {user,loading}=useAuth();const location=useLocation();if(loading)return <div className="py-24 text-center">Loading your account…</div>;return user?children:<Navigate to="/login" replace state={{from:location.pathname}}/>}
